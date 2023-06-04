package shop.wazard.application.port.out;

import java.util.List;
import shop.wazard.application.domain.CompanyInfoForMyPage;
import shop.wazard.dto.GetPastWorkplaceResDto;

public interface CompanyForMyPagePort {

    List<GetPastWorkplaceResDto> getPastWorkplaces(Long accountId);

    CompanyInfoForMyPage findPastCompanyInfo(Long accountId, Long companyId);
}
