package shop.wazard.application.port.out;

import shop.wazard.dto.GetBelongedCompanyResDto;
import shop.wazard.dto.GetOwnedCompanyResDto;

import java.util.List;

public interface RosterForCompanyPort {

    void deleteRoster(Long companyId);

    List<GetOwnedCompanyResDto> getOwnedCompanyList(Long accountId);

    List<GetBelongedCompanyResDto> getBelongedCompanyList(Long accountId);

}
