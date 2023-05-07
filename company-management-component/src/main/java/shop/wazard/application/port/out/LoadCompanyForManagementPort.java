package shop.wazard.application.port.out;

import shop.wazard.application.domain.CompanyForManagement;

public interface LoadCompanyForManagementPort {

    CompanyForManagement findCompanyById(Long id);

}
