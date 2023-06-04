package shop.wazard.adapter.out.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.application.domain.AccountForCompany;
import shop.wazard.application.domain.Company;
import shop.wazard.application.domain.CompanyInfo;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RosterJpa;
import shop.wazard.entity.company.RosterTypeJpa;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {EntityManager.class, CompanyDbAdapter.class, CompanyMapper.class, AccountForCompanyMapper.class, CompanyJpaRepository.class, AccountJpaForCompanyRepository.class, RosterJpaForCompanyRepository.class})
class CompanyDbAdapterTest {

    @Autowired
    private CompanyMapper companyMapper;
    @MockBean
    private AccountForCompanyMapper accountForCompanyMapper;
    @Autowired
    private CompanyJpaRepository companyJpaRepository;
    @Autowired
    private AccountJpaForCompanyRepository accountJpaForCompanyRepository;
    @Autowired
    private RosterJpaForCompanyRepository rosterJpaForCompanyRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("고용주 - 업장 등록 - CompanyJpa 저장")
    public void saveCompanyJpaSuccess() throws Exception {
        // given
        AccountForCompany accountForCompany = AccountForCompany.builder()
                .id(1L)
                .roles("EMPLOYER")
                .email("test@email.com")
                .userName("name")
                .build();
        Company company = Company.builder()
                .companyInfo(
                        CompanyInfo.builder()
                                .companyName("companyName")
                                .zipCode(100)
                                .companyAddress("companyAddress")
                                .companyDetailAddress("companyDetailAddress")
                                .companyContact("02-111-1111")
                                .salaryDate(1)
                                .businessType("type")
                                .logoImageUrl("www.test.com")
                                .build()
                )
                .build();

        // when
        AccountJpa accountJpa = accountJpaForCompanyRepository.findByEmail(accountForCompany.getEmail());
        CompanyJpa result = companyJpaRepository.save(companyMapper.toCompanyJpa(company));
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(company.getCompanyInfo().getCompanyName(), result.getCompanyName()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getZipCode(), result.getZipCode()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getCompanyAddress(), result.getCompanyAddress()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getCompanyDetailAddress(), result.getCompanyDetailAddress()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getCompanyContact(), result.getCompanyContact()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getSalaryDate(), result.getSalaryDate()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getBusinessType(), result.getBusinessType()),
                () -> Assertions.assertEquals(company.getCompanyInfo().getLogoImageUrl(), result.getLogoImageUrl())
        );
    }

    @Test
    @DisplayName("고용주 - 업장 등록 - CompanyAccountRelJpa 저장")
    public void saveCompanyAccountRelJpaSuccess() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployerAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        accountJpaForCompanyRepository.save(accountJpa);
        companyJpaRepository.save(companyJpa);
        RosterJpa rosterJpa = companyMapper.saveRelationInfo(accountJpa, companyJpa, RosterTypeJpa.EMPLOYER);
        RosterJpa result = rosterJpaForCompanyRepository.save(rosterJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.getAccountJpa(), accountJpa),
                () -> Assertions.assertEquals(result.getCompanyJpa(), companyJpa)
        );
    }
    
    @Test
    @DisplayName("고용주 - 업장 정보 수정 - CompanyAccountRel 수정")
    public void updateCompanyInfoSuccess() throws Exception {
        // given
        CompanyJpa companyJpa = setDefaultCompanyJpa();
        Company changedCompany = Company.builder()
                .companyInfo(
                        CompanyInfo.builder()
                                .companyName("testName")
                                .companyAddress("testAddress")
                                .companyContact("031-123-1234")
                                .logoImageUrl("testLogoUrl")
                                .salaryDate(1)
                                .build()
                )
                .build();

        // when
        CompanyJpa savedCompanyJpa = companyJpaRepository.save(companyJpa);
        CompanyJpa result = companyJpaRepository.findById(savedCompanyJpa.getId()).get();
        companyMapper.updateCompanyInfo(result, changedCompany);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(changedCompany.getCompanyInfo().getCompanyName(), result.getCompanyName()),
                () -> Assertions.assertEquals(changedCompany.getCompanyInfo().getCompanyAddress(), result.getCompanyAddress()),
                () -> Assertions.assertEquals(changedCompany.getCompanyInfo().getCompanyContact(), result.getCompanyContact()),
                () -> Assertions.assertEquals(changedCompany.getCompanyInfo().getLogoImageUrl(), result.getLogoImageUrl()),
                () -> Assertions.assertEquals(changedCompany.getCompanyInfo().getSalaryDate(), result.getSalaryDate())
        );
    }

    @Test
    @DisplayName("고용주 - 업장 삭제 - CompanyAccountRelJpa 상태 값 변경")
    public void deleteCompanyAccountRelJpaSuccess() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployerAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForCompanyRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaRepository.save(companyJpa);
        RosterJpa rosterJpa = rosterJpaForCompanyRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYER)
                .build());
        rosterJpaForCompanyRepository.deleteCompanyAccountRel(savedCompanyJpa.getId());
        List<RosterJpa> resultList = rosterJpaForCompanyRepository.findAll();
        em.flush();

        // then
        Assertions.assertEquals("INACTIVE", resultList.get(0).getBaseStatusJpa().getStatus());
    }

    @Test
    @DisplayName("고용주 - 업장 삭제 - CompanyJpa 상태 값 변경")
    public void deleteCompanyJpaSuccess() throws Exception {
        // given
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        CompanyJpa savedCompanyJpa = companyJpaRepository.save(companyJpa);
        savedCompanyJpa.delete();
        em.flush();

        // then
        Assertions.assertEquals("INACTIVE", savedCompanyJpa.getBaseStatusJpa().getStatus());
    }

    @Test
    @DisplayName("고용주 - 운영 업장 리스트 조회 - 리스트 조회")
    public void getOwnedCompanyList() throws Exception {
        // given
        Long accountId = setDefaultOwnedCompanyList();

        // when
        List<CompanyJpa> result = companyJpaRepository.findOwnedCompanyList(accountId);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals("companyName1", result.get(0).getCompanyName()),
                () -> Assertions.assertEquals("companyName2", result.get(1).getCompanyName()),
                () -> Assertions.assertEquals("companyName3", result.get(2).getCompanyName())
        );
    }

    @Test
    @DisplayName("고용주 - 소속 업장 리스트 조회 - 리스트 조회")
    public void getBelongdeCompanyList() throws Exception {
        // given
        Long accountId = setDefaultBelongedCompanyList();

        // when
        List<CompanyJpa> result = companyJpaRepository.findBelongedCompanyList(accountId);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals("companyName1", result.get(0).getCompanyName()),
                () -> Assertions.assertEquals("companyName2", result.get(1).getCompanyName()),
                () -> Assertions.assertEquals("companyName3", result.get(2).getCompanyName())
        );
    }

    private CompanyJpa setDefaultCompanyJpa() {
        return CompanyJpa.builder()
                .companyName("companyName")
                .zipCode(100)
                .companyAddress("companyAddress")
                .companyDetailAddress("companyDetailAddress")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .businessType("type")
                .logoImageUrl("www.test.com")
                .build();
    }

    private AccountJpa setDefaultEmployerAccountJpa() {
        return AccountJpa.builder()
                .email("test@email.com")
                .password("testPwd")
                .userName("testName")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYER")
                .build();
    }

    private Long setDefaultOwnedCompanyList() {
        AccountJpa accountJpa = setDefaultEmployerAccountJpa();
        CompanyJpa companyJpa1 = CompanyJpa.builder()
                .companyName("companyName1")
                .zipCode(100)
                .companyAddress("companyAddress1")
                .companyDetailAddress("companyDetailAddress1")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .businessType("type")
                .logoImageUrl("www.test1.com")
                .build();
        CompanyJpa companyJpa2 = CompanyJpa.builder()
                .companyName("companyName2")
                .zipCode(200)
                .companyAddress("companyAddress2")
                .companyDetailAddress("companyDetailAddress2")
                .companyContact("02-222-2222")
                .salaryDate(2)
                .businessType("type")
                .logoImageUrl("www.test2.com")
                .build();
        CompanyJpa companyJpa3 = CompanyJpa.builder()
                .companyName("companyName3")
                .zipCode(300)
                .companyAddress("companyAddress3")
                .companyDetailAddress("companyDetailAddress3")
                .companyContact("02-333-3333")
                .salaryDate(3)
                .businessType("type")
                .logoImageUrl("www.test3.com")
                .build();

        AccountJpa savedAccountJpa = accountJpaForCompanyRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa1 = companyJpaRepository.save(companyJpa1);
        CompanyJpa savedCompanyJpa2 = companyJpaRepository.save(companyJpa2);
        CompanyJpa savedCompanyJpa3 = companyJpaRepository.save(companyJpa3);

        rosterJpaForCompanyRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa1)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYER)
                .build());
        rosterJpaForCompanyRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa2)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYER)
                .build());
        rosterJpaForCompanyRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa3)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYER)
                .build());
        em.flush();
        return savedAccountJpa.getId();
    }

    private Long setDefaultBelongedCompanyList() {
        AccountJpa accountJpa = setDefaultEmployerAccountJpa();
        CompanyJpa companyJpa1 = CompanyJpa.builder()
                .companyName("companyName1")
                .zipCode(100)
                .companyAddress("companyAddress1")
                .companyDetailAddress("companyDetailAddress1")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .businessType("type")
                .logoImageUrl("www.test1.com")
                .build();
        CompanyJpa companyJpa2 = CompanyJpa.builder()
                .companyName("companyName2")
                .zipCode(200)
                .companyAddress("companyAddress2")
                .companyDetailAddress("companyDetailAddress2")
                .companyContact("02-222-2222")
                .salaryDate(2)
                .businessType("type")
                .logoImageUrl("www.test2.com")
                .build();
        CompanyJpa companyJpa3 = CompanyJpa.builder()
                .companyName("companyName3")
                .zipCode(300)
                .companyAddress("companyAddress3")
                .companyDetailAddress("companyDetailAddress3")
                .companyContact("02-333-3333")
                .salaryDate(3)
                .businessType("type")
                .logoImageUrl("www.test3.com")
                .build();

        AccountJpa savedAccountJpa = accountJpaForCompanyRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa1 = companyJpaRepository.save(companyJpa1);
        CompanyJpa savedCompanyJpa2 = companyJpaRepository.save(companyJpa2);
        CompanyJpa savedCompanyJpa3 = companyJpaRepository.save(companyJpa3);

        rosterJpaForCompanyRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa1)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build());
        rosterJpaForCompanyRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa2)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build());
        rosterJpaForCompanyRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa3)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build());
        em.flush();
        return savedAccountJpa.getId();
    }

}