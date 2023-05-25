package shop.wazard.application.port.out;

import shop.wazard.application.domain.CompanyInfoForMyPage;
import shop.wazard.dto.GetPastWorkplaceResDto;

import java.util.List;

public interface CompanyForMyPagePort {

    List<GetPastWorkplaceResDto> getPastWorkplaces(Long accountId);

    CompanyInfoForMyPage findCompanyByAccountIdAndCompanyId(Long accountId, Long companyId);

}
