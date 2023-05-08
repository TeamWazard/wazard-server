package shop.wazard.application.port.out;

import shop.wazard.application.domain.CompanyForManagement;

public interface CompanyForManagementPort {

    CompanyForManagement findCompanyById(Long id);

    void saveCompany(String email, CompanyForManagement companyForManagement);

    void updateCompanyInfo(CompanyForManagement companyForManagement);

    void deleteCompany(Long companyId);

}
