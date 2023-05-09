package shop.wazard.application.port.out;

import shop.wazard.application.domain.CompanyForManagement;

import java.util.List;

public interface RosterForCompanyManagementPort {

    void deleteRoster(Long companyId);

    List<CompanyForManagement> getOwnedCompanyList(Long accountId);

}
