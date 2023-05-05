package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.Company;

public interface LoadCompanyPort {

    Company findCompanyById(Long id);

}
