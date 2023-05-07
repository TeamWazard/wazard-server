package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.CompanyForManagement;

public interface LoadCompanyPort {

    CompanyForManagement findCompanyById(Long id);

}
