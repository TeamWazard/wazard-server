package shop.wazard.adapter.out.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.domain.Company;
import shop.wazard.application.port.domain.CompanyInfo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CompanyDbAdapter.class})
class CompanyDbAdapterTest {
    @MockBean
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

        //when
        Mockito.when(companyDbAdapter.findAccountByEmail("test@email.com")).thenReturn(account);
        Mockito.doNothing().when(companyDbAdapter).saveCompany(company);
        Mockito.doNothing().when(companyDbAdapter).saveCompanyAccountRel(company, account);

        //then
        Assertions.assertDoesNotThrow(() -> companyDbAdapter.saveCompany(company));
        Assertions.assertDoesNotThrow(() -> companyDbAdapter.saveCompanyAccountRel(company, account));

    }
}