package shop.wazard.application.port.out;

import shop.wazard.application.domain.CompanyForManagement;

public interface UpdateCompanyPort {

    void updateCompanyInfo(CompanyForManagement companyForManagement);

}
