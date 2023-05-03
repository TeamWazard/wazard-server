package shop.wazard.adapter.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.domain.CompanyInfo;
import shop.wazard.entity.company.CompanyJpa;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyDbAdapter.class})
class CompanyDbAdapterTest {
    @Autowired
    private CompanyDbAdapter companyDbAdapter;
    @MockBean
    private CompanyMapper companyMapper;
    @MockBean
    private CompanyJpaRepository companyJpaRepository;

    @Test
    @DisplayName("고용주 - 업장 등록 - 성공")
    public void registerCompanySuccess() throws Exception {
        // given
        Account account = Account.builder()
                .id(1L)
                .roles("EMPLOYER")
                .email("test@email.com")
                .userName("testName")
                .build();
        Company company = Company.builder()
                .companyInfo(
                        CompanyInfo.builder()
                                .companyName("testCompanyName")
                                .companyAddress("testCompanyAddress")
                                .companyContact("031-432-4321")
                                .salaryDate(1)
                                .build()
                )
                .build();
        CompanyJpa companyJpa = companyMapper.toCompanyEntity(company);

        //when
        Mockito.when(companyJpaRepository.save(companyJpa)).thenReturn(companyJpa);
        Mockito.doNothing().when(companyDbAdapter).saveCompany(company);

        //then

    }
}