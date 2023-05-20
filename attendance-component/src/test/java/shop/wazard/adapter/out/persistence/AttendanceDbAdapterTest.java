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
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.commuteRecord.AbsentJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.commuteRecord.ExitRecordJpa;
import shop.wazard.entity.company.CompanyJpa;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {
        EntityManager.class,
        AttendanceDbAdapter.class,
        AttendanceMapper.class,
        AccountForAttendanceMapper.class,
        AccountJpaForAttendanceRepository.class,
        AbsentJpaForAttendanceRepository.class,
        CompanyJpaForAttendanceRepository.class,
        EnterRecordJpaForAttendanceRepository.class,
        ExitRecordJpaForAttendanceRepository.class})
class AttendanceDbAdapterTest {

    @Autowired
    private AttendanceMapper attendanceMapper;
    @Autowired
    private AccountForAttendanceMapper accountForAttendanceMapper;
    @Autowired
    private AccountJpaForAttendanceRepository accountJpaForAttendanceRepository;
    @Autowired
    private CompanyJpaForAttendanceRepository companyJpaForAttendanceRepository;
    @Autowired
    private AbsentJpaForAttendanceRepository absentJpaForAttendanceRepository;
    @Autowired
    private EnterRecordJpaForAttendanceRepository enterRecordJpaForAttendanceRepository;
    @Autowired
    private ExitRecordJpaForAttendanceRepository exitRecordJpaForAttendanceRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("고용주 - 요일별 출근부 조회 - 성공")
    void getAttendancesByDay() throws Exception {
        // given
        CompanyJpa companyJpa = setDefaultCompanyJpa();
        List<AccountJpa> accountJpaList = setDefaultAccountJpaList();

        // when
        CompanyJpa savedCompanyJpa = companyJpaForAttendanceRepository.save(companyJpa);
        List<EnterRecordJpa> enterRecordJpaList = setDefaultEnterRecordJpaList(accountJpaList, savedCompanyJpa);
        List<ExitRecordJpa> exitRecordJpaList = setDefaultExitRecordJpaList(enterRecordJpaList);
        em.flush();
        em.clear();
        List<EnterRecordJpa> findEnter = enterRecordJpaForAttendanceRepository
                .findAllByCompanyJpaAndEnterDateOrderByAccountJpaAsc(savedCompanyJpa, enterRecordJpaList.get(0).getEnterDate());

        // then
        Assertions.assertEquals(enterRecordJpaList.get(0).getEnterDate(), findEnter.get(0).getEnterDate());
        Assertions.assertEquals(enterRecordJpaList.get(0).getEnterTime(), findEnter.get(0).getEnterTime());
        Assertions.assertEquals(exitRecordJpaList.get(0).getExitDate(), findEnter.get(0).getExitRecordJpa().getExitDate());
        Assertions.assertEquals(exitRecordJpaList.get(0).getExitTime(), findEnter.get(0).getExitRecordJpa().getExitTime());
        Assertions.assertEquals(accountJpaList.get(1).getUserName(), findEnter.get(1).getAccountJpa().getUserName());
    }

    @Test
    @DisplayName("고용주 - 근무자 결석 처리 - AbsentJpa 저장")
    void markingAbsentSuccess() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForAttendanceRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForAttendanceRepository.save(companyJpa);
        AbsentJpa absentJpa = absentJpaForAttendanceRepository.save(AbsentJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa)
                .absentDate(LocalDate.now())
                .build());

        // then
        Assertions.assertEquals(savedAccountJpa.getEmail(), absentJpa.getAccountJpa().getEmail());
        Assertions.assertEquals(savedCompanyJpa.getCompanyName(), absentJpa.getCompanyJpa().getCompanyName());
        Assertions.assertEquals(LocalDate.now().getDayOfWeek(), absentJpa.getAbsentDate().getDayOfWeek());
    }

    @Test
    @DisplayName("근무자 - 정상 출근 기록 - CommuteRecordJpa 저장")
    public void saveCommuteRecordJpaSuccess_ON() throws Exception {
        // given
        EnterRecord enterRecord = EnterRecord.builder()
                .accountId(1L)
                .companyId(2L)
                .tardy(false)
                .commuteTime(LocalDateTime.of(2023, 1, 1, 12, 12, 12))
                .build();
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForAttendanceRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForAttendanceRepository.save(companyJpa);
        EnterRecordJpa enterRecordJpa = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(enterRecord.isTardy())
                .enterTime(enterRecord.getCommuteTime())
                .build();
        EnterRecordJpa result = enterRecordJpaForAttendanceRepository.save(enterRecordJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(accountJpa, result.getAccountJpa()),
                () -> Assertions.assertEquals(companyJpa, result.getCompanyJpa()),
                () -> Assertions.assertEquals(enterRecord.getCommuteTime(), result.getEnterTime()),
                () -> Assertions.assertEquals(enterRecord.isTardy(), result.isTardy())
        );
    }

    @Test
    @DisplayName("근무자 - 지각 출근 기록 - CommuteRecordJpa 저장")
    public void saveCommuteRecordJpaSuccess_LATE() throws Exception {
        // given
        EnterRecord enterRecord = EnterRecord.builder()
                .accountId(1L)
                .companyId(2L)
                .tardy(true)
                .commuteTime(LocalDateTime.of(2023, 1, 1, 12, 12, 12))
                .build();
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForAttendanceRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForAttendanceRepository.save(companyJpa);
        EnterRecordJpa enterRecordJpa = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(enterRecord.isTardy())
                .enterTime(enterRecord.getCommuteTime())
                .build();
        EnterRecordJpa result = enterRecordJpaForAttendanceRepository.save(enterRecordJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(accountJpa, result.getAccountJpa()),
                () -> Assertions.assertEquals(companyJpa, result.getCompanyJpa()),
                () -> Assertions.assertEquals(enterRecord.getCommuteTime(), result.getEnterTime()),
                () -> Assertions.assertEquals(enterRecord.isTardy(), result.isTardy())
        );
    }

    @Test
    @DisplayName("근무자 - 퇴근 기록 - CommuteRecordJpa 저장")
    public void saveCommuteRecordJpaSuccess_OFF() throws Exception {
        // given
        EnterRecord enterRecord = EnterRecord.builder()
                .accountId(1L)
                .companyId(2L)
                .tardy(false)
                .commuteTime(LocalDateTime.of(2023, 1, 1, 12, 12, 12))
                .build();
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForAttendanceRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForAttendanceRepository.save(companyJpa);
        EnterRecordJpa enterRecordJpa = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(enterRecord.isTardy())
                .enterTime(enterRecord.getCommuteTime())
                .build();
        EnterRecordJpa result = enterRecordJpaForAttendanceRepository.save(enterRecordJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(accountJpa, result.getAccountJpa()),
                () -> Assertions.assertEquals(companyJpa, result.getCompanyJpa()),
                () -> Assertions.assertEquals(enterRecord.getCommuteTime(), result.getEnterTime()),
                () -> Assertions.assertEquals(enterRecord.isTardy(), result.isTardy())
        );
    }

    private AccountJpa setDefaultEmployerAccountJpa() {
        return AccountJpa.builder()
                .email("testEmployer@email.com")
                .password("testPwd")
                .userName("testName1")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYER")
                .build();
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

    private CompanyJpa setDefaultCompanyJpa() {
        return CompanyJpa.builder()
                .companyName("companyName")
                .companyAddress("companyAddress")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .logoImageUrl("www.test.com")
                .build();
    }

    private List<AccountJpa> setDefaultAccountJpaList() {
        List<AccountJpa> accountJpaList = new ArrayList<>();
        AccountJpa accountJpa1 = AccountJpa.builder()
                .email("testEmployee@email.com")
                .password("testPwd")
                .userName("testName2")
                .phoneNumber("010-2222-2222")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        AccountJpa accountJpa2 = AccountJpa.builder()
                .email("testEmployee@email.com")
                .password("testPwd")
                .userName("testName3")
                .phoneNumber("010-3333-3333")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        accountJpaList.add(accountJpaForAttendanceRepository.save(accountJpa1));
        accountJpaList.add(accountJpaForAttendanceRepository.save(accountJpa2));
        return accountJpaList;
    }

    private List<EnterRecordJpa> setDefaultEnterRecordJpaList(List<AccountJpa> accountJpaList, CompanyJpa companyJpa) {
        List<EnterRecordJpa> enterRecordJpaList = new ArrayList<>();
        EnterRecordJpa enterRecordJpa1 = EnterRecordJpa.builder()
                .accountJpa(accountJpaList.get(0))
                .companyJpa(companyJpa)
                .tardy(false)
                .enterTime(LocalDateTime.now())
                .enterDate(LocalDate.now())
                .build();
        EnterRecordJpa enterRecordJpa2 = EnterRecordJpa.builder()
                .accountJpa(accountJpaList.get(1))
                .companyJpa(companyJpa)
                .tardy(false)
                .enterTime(LocalDateTime.now())
                .enterDate(LocalDate.now())
                .build();
        enterRecordJpaList.add(enterRecordJpaForAttendanceRepository.save(enterRecordJpa1));
        enterRecordJpaList.add(enterRecordJpaForAttendanceRepository.save(enterRecordJpa2));
        return enterRecordJpaList;
    }

    private List<ExitRecordJpa> setDefaultExitRecordJpaList(List<EnterRecordJpa> enterRecordJpaList) {
        List<ExitRecordJpa> exitRecordJpaList = new ArrayList<>();
        ExitRecordJpa exitRecordJpa1 = ExitRecordJpa.builder()
                .enterRecordJpa(enterRecordJpaList.get(0))
                .exitTime(LocalDateTime.now())
                .exitDate(LocalDate.now())
                .build();
        ExitRecordJpa exitRecordJpa2 = ExitRecordJpa.builder()
                .enterRecordJpa(enterRecordJpaList.get(1))
                .exitTime(LocalDateTime.now())
                .exitDate(LocalDate.now())
                .build();
        exitRecordJpaList.add(exitRecordJpaForAttendanceRepository.save(exitRecordJpa1));
        exitRecordJpaList.add(exitRecordJpaForAttendanceRepository.save(exitRecordJpa2));
        return exitRecordJpaList;
    }

}