package shop.wazard.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForMyPage;
import shop.wazard.application.domain.CompanyInfoForMyPage;
import shop.wazard.application.domain.WorkRecordForMyPage;
import shop.wazard.application.port.in.MyPageService;
import shop.wazard.application.port.out.AccountForMyPagePort;
import shop.wazard.application.port.out.CompanyForMyPagePort;
import shop.wazard.application.port.out.RosterForMyPagePort;
import shop.wazard.application.port.out.WorkRecordForMyPagePort;
import shop.wazard.dto.*;
import shop.wazard.util.calculator.Calculator;

@Transactional
@Service
@RequiredArgsConstructor
class MyPageServiceImpl implements MyPageService {

    private final AccountForMyPagePort accountForMyPagePort;
    private final RosterForMyPagePort rosterForMyPagePort;
    private final CompanyForMyPagePort companyForMyPagePort;
    private final WorkRecordForMyPagePort workRecordForMyPagePort;

    @Transactional(readOnly = true)
    @Override
    public List<GetPastWorkplaceResDto> getPastWorkplaces(
            GetPastWorkplaceReqDto getPastWorkplaceReqDto) {
        AccountForMyPage accountForMyPage =
                accountForMyPagePort.findAccountByEmail(getPastWorkplaceReqDto.getEmail());
        accountForMyPage.checkIsEmployee();
        return companyForMyPagePort.getPastWorkplaces(accountForMyPage.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public GetMyPastWorkRecordResDto getMyPastWorkingRecord(
            GetMyPastWorkRecordReqDto getMyPastWorkingRecordReqDto) {
        AccountForMyPage accountForMyPage =
                accountForMyPagePort.findAccountByEmail(getMyPastWorkingRecordReqDto.getEmail());
        accountForMyPage.checkIsEmployee();
        CompanyInfoForMyPage companyInfoForMyPage =
                companyForMyPagePort.findPastCompanyInfo(
                        accountForMyPage.getId(), getMyPastWorkingRecordReqDto.getCompanyId());
        WorkRecordForMyPage workRecordForMyPage =
                workRecordForMyPagePort.getMyPastWorkRecord(
                        accountForMyPage.getId(), getMyPastWorkingRecordReqDto.getCompanyId());
        double workScore =
                Calculator.getAttitudeScore(
                        workRecordForMyPage.getTardyCount(),
                        workRecordForMyPage.getAbsentCount(),
                        workRecordForMyPage.getWorkDayCount());
        return GetMyPastWorkRecordResDto.builder()
                .companyName(companyInfoForMyPage.getCompanyName())
                .companyAddress(companyInfoForMyPage.getCompanyAddress())
                .companyContact(companyInfoForMyPage.getCompanyContact())
                .companyLogoImage(companyInfoForMyPage.getLogoImageUrl())
                .tardyCount(workRecordForMyPage.getTardyCount())
                .absentCount(workRecordForMyPage.getAbsentCount())
                .workScore(workScore)
                .startWorkingDate(workRecordForMyPage.getStartWorkingDate())
                .endWorkingDate(workRecordForMyPage.getEndWorkingDate())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public GetMyAttitudeScoreResDto getMyAttitudeScore(
            GetMyAttitudeScoreReqDto getMyAttitudeScoreReqDto) {
        AccountForMyPage accountForMyPage =
                accountForMyPagePort.findAccountByEmail(getMyAttitudeScoreReqDto.getEmail());
        accountForMyPage.checkIsEmployee();
        // accountId를 통해 roster에서 companyList를 반환 받은 후 그 아이디값들을 통해 지각횟수, 무단결석횟수, 총 근무일수를 가져옴
        List<WorkRecordForMyPage> myTotalPastRecord =
                workRecordForMyPagePort.getMyTotalPastRecord(accountForMyPage.getId());
        List<Double> totalMyAttitudeScores = getCalculatedAttitudeScore(myTotalPastRecord);
        double myAttitudeScore = Calculator.getAverageAttitudeScore(totalMyAttitudeScores);
        return GetMyAttitudeScoreResDto.builder().myAttitudeScore(myAttitudeScore).build();
    }

    private List<Double> getCalculatedAttitudeScore(List<WorkRecordForMyPage> myTotalPastRecord) {
        return myTotalPastRecord.stream()
                .map(
                        record ->
                                Calculator.getAttitudeScore(
                                        record.getTardyCount(),
                                        record.getAbsentCount(),
                                        record.getWorkDayCount()))
                .collect(Collectors.toList());
    }
}
