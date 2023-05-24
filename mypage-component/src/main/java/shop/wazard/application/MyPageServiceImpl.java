package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.domain.AccountForMyPage;
import shop.wazard.application.port.in.MyPageService;
import shop.wazard.application.port.out.AccountForMyPagePort;
import shop.wazard.application.port.out.CompanyForMyPagePort;
import shop.wazard.application.port.out.RosterForMyPagePort;
import shop.wazard.dto.GetPastWorkplaceReqDto;
import shop.wazard.dto.GetPastWorkplaceResDto;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
class MyPageServiceImpl implements MyPageService {

    private final AccountForMyPagePort accountForMyPagePort;
    private final RosterForMyPagePort rosterForMyPagePort;
    private final CompanyForMyPagePort companyForMyPagePort;

    @Transactional(readOnly = true)
    @Override
    public List<GetPastWorkplaceResDto> getPastWorkplaces(GetPastWorkplaceReqDto getPastWorkplaceReqDto) {
        AccountForMyPage accountForMyPage = accountForMyPagePort.findAccountByEmail(getPastWorkplaceReqDto.getEmail());
        accountForMyPage.checkIsEmployee();
        return companyForMyPagePort.getPastWorkplaces(accountForMyPage.getId());
    }

}
