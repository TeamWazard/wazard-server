package shop.wazard.application;

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
import shop.wazard.dto.GetMyPastWorkRecordReqDto;
import shop.wazard.dto.GetMyPastWorkRecordResDto;
import shop.wazard.dto.GetPastWorkplaceReqDto;
import shop.wazard.dto.GetPastWorkplaceResDto;
import shop.wazard.util.calculator.Calculator;

import java.util.List;

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
    public List<GetPastWorkplaceResDto> getPastWorkplaces(GetPastWorkplaceReqDto getPastWorkplaceReqDto) {
        AccountForMyPage accountForMyPage = accountForMyPagePort.findAccountByEmail(getPastWorkplaceReqDto.getEmail());
        accountForMyPage.checkIsEmployee();
        return companyForMyPagePort.getPastWorkplaces(accountForMyPage.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public GetMyPastWorkRecordResDto getMyPastWorkRecord(GetMyPastWorkRecordReqDto getMyPastWorkRecordReqDto) {
        AccountForMyPage accountForMyPage = accountForMyPagePort.findAccountByEmail(getMyPastWorkRecordReqDto.getEmail());
        accountForMyPage.checkIsEmployee();
        CompanyInfoForMyPage companyInfoForMyPage = companyForMyPagePort.findPastCompanyInfoByAccountIdAndCompanyId(accountForMyPage.getId(), getMyPastWorkRecordReqDto.getCompanyId());
        WorkRecordForMyPage workRecordForMyPage = workRecordForMyPagePort.getMyPastWorkRecord(accountForMyPage.getId(), getMyPastWorkRecordReqDto.getCompanyId());
        double workScore = Calculator.calculateAttitudeScore(workRecordForMyPage.getTardyCount(), workRecordForMyPage.getAbsentCount(), workRecordForMyPage.getWorkDayCount());
        return GetMyPastWorkRecordResDto.builder()
                .companyName(companyInfoForMyPage.getCompanyName())
                .companyAddress(companyInfoForMyPage.getCompanyAddress())
                .companyContact(companyInfoForMyPage.getCompanyContact())
                .companyLogoImage(companyInfoForMyPage.getLogoImageUrl())
                .tardyCount(workRecordForMyPage.getTardyCount())
                .absentCount(workRecordForMyPage.getAbsentCount())
                .workScore(workScore)
                .startWorkDate(workRecordForMyPage.getStartWorkDate())
                .endWorkDate(workRecordForMyPage.getEndWorkDate())
                .build();
    }
}
