package shop.wazard.application.port.out;

import java.util.List;
import shop.wazard.dto.GetBelongedCompanyResDto;
import shop.wazard.dto.GetOwnedCompanyResDto;

public interface RosterForCompanyPort {

    void deleteRoster(Long companyId);

    List<GetOwnedCompanyResDto> getOwnedCompanyList(Long accountId);

    List<GetBelongedCompanyResDto> getBelongedCompanyList(Long accountId);
}
