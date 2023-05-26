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
import shop.wazard.application.domain.CompanyInfoForMyPage;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity.BaseStatusJpa;
import shop.wazard.entity.commuteRecord.AbsentJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.commuteRecord.ExitRecordJpa;
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
        EnterRecordJpaForMyPageRepository.class,
        ExitRecordJpaForMyPageRepository.class,
        AbsentJpaForMyPageRepository.class,
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
    private EnterRecordJpaForMyPageRepository enterRecordJpaForMyPageRepository;
    @Autowired
    private ExitRecordJpaForMyPageRepository exitRecordJpaForMyPageRepository;
    @Autowired
    private AbsentJpaForMyPageRepository absentJpaForMyPageRepository;
    @Autowired
    private EntityManager em;


    @Test
    @DisplayName("근무자 - 과거 근무지 조회 - CompanyJpa 엔티티 조회")
    void getPastWorkplaces() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        List<CompanyJpa> companyJpaList = setDefaultCompanyJpaList();

        // when
        AccountJpa savedAccountJpa = accountJpaForMyPageRepository.save(accountJpa);
        List<RosterJpa> rosterJpaList = setDefaultRosterJpaListForGetPastWorkplaces(savedAccountJpa, companyJpaList);
        // 상태 변경을 위한 단순 참조 (따로 메서드 안빼기 위해서 재사용한 )
        rosterJpaList.get(0).updateRosterStateForExile(BaseStatusJpa.INACTIVE);
        rosterJpaList.get(1).updateRosterStateForExile(BaseStatusJpa.INACTIVE);
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

    @Test
    @DisplayName("근로자 - 과거 근무 기록 상세 조회를 위한 업장 정보 조회 - CompanyJpa 조회")
    void getCompanyForGetMyPastWorkRecordAPI() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForMyPageRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForMyPageRepository.save(companyJpa);
        RosterJpa rosterJpa = setDefaultRosterJpa(savedAccountJpa, savedCompanyJpa);
        rosterJpa.updateRosterStateForExile(BaseStatusJpa.INACTIVE);
        CompanyJpa findCompanyJpa = companyJpaForMyPageRepository.findPastCompanyJpaByAccountIdAndCompanyId(savedAccountJpa.getId(), savedCompanyJpa.getId()).get();
        CompanyInfoForMyPage companyInfoForMyPage = myPageMapper.createCompanyInfoForMyPage(findCompanyJpa);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(savedAccountJpa.getUserName(), rosterJpa.getAccountJpa().getUserName()),
                () -> Assertions.assertEquals(savedCompanyJpa.getCompanyName(), rosterJpa.getCompanyJpa().getCompanyName()),
                () -> Assertions.assertEquals(companyInfoForMyPage.getCompanyName(), findCompanyJpa.getCompanyName())
        );
    }

    @Test
    @DisplayName("근로자 - 총 지각 횟수 조회 - EnterRecordJpa 조회")
    void getTardyCount() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForMyPageRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForMyPageRepository.save(companyJpa);
        List<EnterRecordJpa> savedEnterRecordJpa = setDefaultEnterRecordJpa(accountJpa, savedCompanyJpa);
        int result = enterRecordJpaForMyPageRepository.countTardyByAccountIdAndCompanyId(savedAccountJpa.getId(), savedCompanyJpa.getId());

        // then
        Assertions.assertEquals(result, 2);
    }

    @Test
    @DisplayName("근로자 - 총 결석 횟수 조회 - AbsentJpa 조회")
    void getAbsentCount() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForMyPageRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForMyPageRepository.save(companyJpa);
        List<AbsentJpa> absentJpaList = setDefaultAbsentJpaList(savedAccountJpa, savedCompanyJpa);
        int result = absentJpaForMyPageRepository.countAbsentByAccountIdAndCompanyId(savedAccountJpa.getId(), savedCompanyJpa.getId());

        // then
        Assertions.assertEquals(result, 2);
    }

    @Test
    @DisplayName("근로자 - 처음 근무 시작한 날 조회 - EnterRecordJpa 조회")
    void getStartWorkDate() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForMyPageRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForMyPageRepository.save(companyJpa);
        RosterJpa rosterJpa = setDefaultRosterJpa(savedAccountJpa, savedCompanyJpa);
        List<EnterRecordJpa> enterRecordJpa = setDefaultEnterRecordJpa(savedAccountJpa, savedCompanyJpa);
        EnterRecordJpa startWorkDate = enterRecordJpaForMyPageRepository.findTopByAccountJpaAndCompanyJpaAndBaseStatusJpaOrderByIdAsc(savedAccountJpa, savedCompanyJpa, BaseStatusJpa.ACTIVE);

        // then
        Assertions.assertEquals(startWorkDate.getEnterDate(), enterRecordJpa.get(0).getEnterDate());
    }

    @Test
    @DisplayName("근로자 - 근무 계약 끝난 날짜 조회 - ExitRecordJpa 조회")
    void getEndWorkDate() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForMyPageRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForMyPageRepository.save(companyJpa);
        RosterJpa rosterJpa = setDefaultRosterJpa(savedAccountJpa, savedCompanyJpa);
        List<EnterRecordJpa> enterRecordJpaList = setDefaultEnterRecordJpa(savedAccountJpa, savedCompanyJpa);
        List<ExitRecordJpa> exitRecordJpaList = setDefaultExitRecordJpa(enterRecordJpaList);
        EnterRecordJpa enterRecordJpa = enterRecordJpaForMyPageRepository.findTopByAccountJpaAndCompanyJpaAndBaseStatusJpaOrderByIdDesc(savedAccountJpa, savedCompanyJpa, BaseStatusJpa.ACTIVE);
        ExitRecordJpa exitRecordJpa = exitRecordJpaForMyPageRepository.findTopByEnterRecordJpaAndBaseStatusJpaOrderByIdDesc(enterRecordJpa, BaseStatusJpa.ACTIVE);

        // then
        Assertions.assertEquals(enterRecordJpaList.get(2).getId(), enterRecordJpa.getId());
        Assertions.assertEquals(exitRecordJpaList.get(2).getExitDate(), exitRecordJpa.getExitDate());
    }

    @Test
    @DisplayName("근로자 - 총 근무한 일수 조회 - EnterRecordJpa 조회")
    void getWorkDayCount() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForMyPageRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForMyPageRepository.save(companyJpa);
        RosterJpa rosterJpa = setDefaultRosterJpa(savedAccountJpa, savedCompanyJpa);
        List<EnterRecordJpa> enterRecordJpaList = setDefaultEnterRecordJpaForGettingTotalWorkDay(savedAccountJpa, savedCompanyJpa);
        EnterRecordJpa enterRecordJpa = enterRecordJpaForMyPageRepository.findTopByAccountJpaAndCompanyJpaAndBaseStatusJpaOrderByIdDesc(savedAccountJpa, savedCompanyJpa, BaseStatusJpa.ACTIVE);
        int result = enterRecordJpaForMyPageRepository.countTotalWorkDayByAccountIdAndCompanyId(savedAccountJpa.getId(), savedCompanyJpa.getId());

        // then
        Assertions.assertEquals(result, 3);
    }

    private List<ExitRecordJpa> setDefaultExitRecordJpa(List<EnterRecordJpa> enterRecordJpaList) {
        List<ExitRecordJpa> exitRecordJpaList = new ArrayList<>();
        ExitRecordJpa exitRecordJpa1 = ExitRecordJpa.builder()
                .enterRecordJpa(enterRecordJpaList.get(0))
                .exitDate(LocalDate.of(2023, 10, 1))
                .build();
        ExitRecordJpa exitRecordJpa2 = ExitRecordJpa.builder()
                .enterRecordJpa(enterRecordJpaList.get(1))
                .exitDate(LocalDate.of(2023, 10, 3))
                .build();
        ExitRecordJpa exitRecordJpa3 = ExitRecordJpa.builder()
                .enterRecordJpa(enterRecordJpaList.get(2))
                .exitDate(LocalDate.of(2023, 10, 5))
                .build();
        ExitRecordJpa savedExitRecordJpa1 = exitRecordJpaForMyPageRepository.save(exitRecordJpa1);
        ExitRecordJpa savedExitRecordJpa2 = exitRecordJpaForMyPageRepository.save(exitRecordJpa2);
        ExitRecordJpa savedExitRecordJpa3 = exitRecordJpaForMyPageRepository.save(exitRecordJpa3);
        exitRecordJpaList.add(savedExitRecordJpa1);
        exitRecordJpaList.add(savedExitRecordJpa2);
        exitRecordJpaList.add(savedExitRecordJpa3);
        return exitRecordJpaList;
    }

    private RosterJpa setDefaultRosterJpa(AccountJpa accountJpa, CompanyJpa companyJpa) {
        RosterJpa rosterJpa = RosterJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build();
        RosterJpa savedRosterJpa = rosterJpaForMyPageRepository.save(rosterJpa);
        return savedRosterJpa;
    }

    private List<AbsentJpa> setDefaultAbsentJpaList(AccountJpa accountJpa, CompanyJpa companyJpa) {
        List<AbsentJpa> absentJpaList = new ArrayList<>();
        AbsentJpa absentJpa1 = AbsentJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .absentDate(LocalDate.of(2023,2,10))
                .build();
        AbsentJpa absentJpa2 = AbsentJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .absentDate(LocalDate.of(2023,3,10))
                .build();
        AbsentJpa savedAbsent1 = absentJpaForMyPageRepository.save(absentJpa1);
        AbsentJpa savedAbsent2 = absentJpaForMyPageRepository.save(absentJpa2);
        absentJpaList.add(savedAbsent1);
        absentJpaList.add(savedAbsent2);
        return absentJpaList;
    }

    private List<AccountJpa> setDefaultEmployeeAccountJpaList() {
        List<AccountJpa> accountJpaList = new ArrayList<>();
        AccountJpa accountJpa1 = AccountJpa.builder()
                .email("testEmployee@email.com")
                .password("testPwd")
                .userName("testName1")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        AccountJpa accountJpa2 = AccountJpa.builder()
                .email("testEmployee2@email.com")
                .password("testPwd2")
                .userName("testName2")
                .phoneNumber("010-2222-2222")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 2, 1))
                .baseStatusJpa(BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        AccountJpa accountJpa3 = AccountJpa.builder()
                .email("testEmployee3@email.com")
                .password("testPwd3")
                .userName("testName3")
                .phoneNumber("010-3333-3333")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 3, 1))
                .baseStatusJpa(BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        AccountJpa savedAccountJpa1 = accountJpaForMyPageRepository.save(accountJpa1);
        AccountJpa savedAccountJpa2 = accountJpaForMyPageRepository.save(accountJpa2);
        AccountJpa savedAccountJpa3 = accountJpaForMyPageRepository.save(accountJpa3);
        accountJpaList.add(savedAccountJpa1);
        accountJpaList.add(savedAccountJpa2);
        accountJpaList.add(savedAccountJpa3);
        return accountJpaList;
    }

    private List<EnterRecordJpa> setDefaultEnterRecordJpa(AccountJpa accountJpa, CompanyJpa companyJpa) {
        List<EnterRecordJpa> enterRecordJpaList = new ArrayList<>();
        EnterRecordJpa enterRecordJpa1 = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(true)
                .enterDate(LocalDate.of(2023, 2, 20))
                .build();
        EnterRecordJpa enterRecordJpa2 = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(true)
                .enterDate(LocalDate.of(2023, 3, 2))
                .build();
        EnterRecordJpa enterRecordJpa3 = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(false)
                .enterDate(LocalDate.of(2023, 3, 5))
                .build();
        EnterRecordJpa savedEnterRecordJpa1 = enterRecordJpaForMyPageRepository.save(enterRecordJpa1);
        EnterRecordJpa savedEnterRecordJpa2 = enterRecordJpaForMyPageRepository.save(enterRecordJpa2);
        EnterRecordJpa savedEnterRecordJpa3 = enterRecordJpaForMyPageRepository.save(enterRecordJpa3);
        enterRecordJpaList.add(savedEnterRecordJpa1);
        enterRecordJpaList.add(savedEnterRecordJpa2);
        enterRecordJpaList.add(savedEnterRecordJpa3);
        return enterRecordJpaList;
    }

    private List<EnterRecordJpa> setDefaultEnterRecordJpaForGettingTotalWorkDay(AccountJpa accountJpa, CompanyJpa companyJpa) {
        List<EnterRecordJpa> enterRecordJpaList = new ArrayList<>();
        EnterRecordJpa enterRecordJpa1 = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(true)
                .enterDate(LocalDate.of(2023, 2, 20))
                .build();
        EnterRecordJpa enterRecordJpa2 = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(true)
                .enterDate(LocalDate.of(2023, 2, 20))
                .build();
        EnterRecordJpa enterRecordJpa3 = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(true)
                .enterDate(LocalDate.of(2023, 3, 2))
                .build();
        EnterRecordJpa enterRecordJpa4 = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(false)
                .enterDate(LocalDate.of(2023, 3, 5))
                .build();
        EnterRecordJpa enterRecordJpa5 = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(false)
                .enterDate(LocalDate.of(2023, 3, 5))
                .build();
        EnterRecordJpa savedEnterRecordJpa1 = enterRecordJpaForMyPageRepository.save(enterRecordJpa1);
        EnterRecordJpa savedEnterRecordJpa2 = enterRecordJpaForMyPageRepository.save(enterRecordJpa2);
        EnterRecordJpa savedEnterRecordJpa3 = enterRecordJpaForMyPageRepository.save(enterRecordJpa3);
        EnterRecordJpa savedEnterRecordJpa4 = enterRecordJpaForMyPageRepository.save(enterRecordJpa4);
        EnterRecordJpa savedEnterRecordJpa5 = enterRecordJpaForMyPageRepository.save(enterRecordJpa5);
        enterRecordJpaList.add(savedEnterRecordJpa1);
        enterRecordJpaList.add(savedEnterRecordJpa2);
        enterRecordJpaList.add(savedEnterRecordJpa3);
        enterRecordJpaList.add(savedEnterRecordJpa4);
        enterRecordJpaList.add(savedEnterRecordJpa5);
        return enterRecordJpaList;
    }

    private CompanyJpa setDefaultCompanyJpa() {
        CompanyJpa companyJpa = CompanyJpa.builder()
                .companyName("companyName1")
                .companyAddress("companyAddress1")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .logoImageUrl("www.test1.com")
                .build();
        return companyJpa;
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
                .baseStatusJpa(BaseStatusJpa.ACTIVE)
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