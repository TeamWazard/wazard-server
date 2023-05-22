package shop.wazard.adapter.out.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RosterJpa;
import shop.wazard.entity.company.RosterTypeJpa;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {
        MyPageDbAdapter.class,
        AccountForMyPageMapper.class,
        MyPageMapper.class,
        AccountJpaForMyPageRepository.class,
        CompanyJpaForMyPageRepository.class,
        RosterJpaForMyPageRepository.class,
        EntityManager.class})
class MyPageDbAdapterTest {

    @Autowired
    private MyPageDbAdapter myPageDbAdapter;
    @Autowired
    private AccountForMyPageMapper accountForMyPageMapper;
    @Autowired
    private MyPageMapper myPageMapper;
    @Autowired
    private AccountJpaForMyPageRepository accountJpaForMyPageRepository;
    @Autowired
    private CompanyJpaForMyPageRepository companyJpaForMyPageRepository;
    @Autowired
    private RosterJpaForMyPageRepository rosterJpaForMyPageRepository;
    @Autowired
    private EntityManager em;


    @Test
    @DisplayName("근무자 - 과거 근무지 조회 - 성공")
    void getPastWorkplaces() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        List<CompanyJpa> companyJpaList = setDefaultCompanyJpaList();

        // when
        AccountJpa savedAccountJpa = accountJpaForMyPageRepository.save(accountJpa);
        List<RosterJpa> rosterJpaList = setDefaultRosterJpaListForGetPastWorkplaces(savedAccountJpa, companyJpaList);
        // 상태 변경을 위한 단순 참조 (따로 메서드 안빼기 위해서 재사용한 )
        rosterJpaList.get(0).updateRosterStateForExile(BaseEntity.BaseStatusJpa.INACTIVE);
        rosterJpaList.get(1).updateRosterStateForExile(BaseEntity.BaseStatusJpa.INACTIVE);
        em.flush();
        em.clear();
        List<CompanyJpa> result = rosterJpaForMyPageRepository.findPastWorkplacesById(savedAccountJpa.getId());

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.get(0).getId(), rosterJpaList.get(0).getCompanyJpa().getId()),
                () -> Assertions.assertEquals(result.get(0).getCompanyName(), rosterJpaList.get(0).getCompanyJpa().getCompanyName()),
                () -> Assertions.assertEquals(result.get(1).getId(), rosterJpaList.get(1).getCompanyJpa().getId()),
                () -> Assertions.assertEquals(result.get(1).getCompanyName(), rosterJpaList.get(1).getCompanyJpa().getCompanyName())
        );
    }

    private List<RosterJpa> setDefaultRosterJpaListForGetPastWorkplaces(AccountJpa accountJpa, List<CompanyJpa> companyJpaList) {
        List<RosterJpa> rosterJpaList = new ArrayList<>();
        RosterJpa rosterJpa1 = RosterJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpaList.get(0))
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build();
        RosterJpa rosterJpa2 = RosterJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpaList.get(1))
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build();
        RosterJpa savedRosterJpa1 = rosterJpaForMyPageRepository.save(rosterJpa1);
        RosterJpa savedRosterJpa2 = rosterJpaForMyPageRepository.save(rosterJpa2);
        rosterJpaList.add(savedRosterJpa1);
        rosterJpaList.add(savedRosterJpa2);
        return rosterJpaList;
    }

    private AccountJpa setDefaultEmployeeAccountJpa() {
        return AccountJpa.builder()
                .email("testEmployee@email.com")
                .password("testPwd")
                .userName("testName2")
                .phoneNumber("010-2222-2222")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
    }

    private List<CompanyJpa> setDefaultCompanyJpaList() {
        List<CompanyJpa> companyJpaList = new ArrayList<>();
        CompanyJpa companyJpa1 = CompanyJpa.builder()
                .companyName("companyName1")
                .companyAddress("companyAddress1")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .logoImageUrl("www.test1.com")
                .build();
        CompanyJpa companyJpa2 = CompanyJpa.builder()
                .companyName("companyName2")
                .companyAddress("companyAddress2")
                .companyContact("02-222-2222")
                .salaryDate(1)
                .logoImageUrl("www.test2.com")
                .build();
        CompanyJpa savedCompanyJpa1 = companyJpaForMyPageRepository.save(companyJpa1);
        CompanyJpa savedCompanyJpa2 = companyJpaForMyPageRepository.save(companyJpa2);
        companyJpaList.add(savedCompanyJpa1);
        companyJpaList.add(savedCompanyJpa2);
        return companyJpaList;
    }

}