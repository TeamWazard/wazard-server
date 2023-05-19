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
        ExitRecordJpaForAttendanceRepository.class
})
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
    @DisplayName("근무자 - 정상 출근 기록 - EnterRecordJpa 저장")
    public void saveEnterRecordJpaSuccess_ON() throws Exception {
        // given
        EnterRecord enterRecord = EnterRecord.builder()
                .accountId(1L)
                .companyId(2L)
                .tardy(false)
                .enterTime(LocalDateTime.of(2023, 1, 1, 12, 12, 12))
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
                .enterTime(enterRecord.getEnterTime())
                .build();
        EnterRecordJpa result = enterRecordJpaForAttendanceRepository.save(enterRecordJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(accountJpa, result.getAccountJpa()),
                () -> Assertions.assertEquals(companyJpa, result.getCompanyJpa()),
                () -> Assertions.assertEquals(enterRecord.getEnterTime(), result.getEnterTime()),
                () -> Assertions.assertEquals(enterRecord.isTardy(), result.isTardy())
        );
    }

    @Test
    @DisplayName("근무자 - 지각 출근 기록 - EnterRecordJpa 저장")
    public void saveEnterRecordJpaSuccess_LATE() throws Exception {
        // given
        EnterRecord enterRecord = EnterRecord.builder()
                .accountId(1L)
                .companyId(2L)
                .tardy(true)
                .enterTime(LocalDateTime.of(2023, 1, 1, 12, 12, 12))
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
                .enterTime(enterRecord.getEnterTime())
                .build();
        EnterRecordJpa result = enterRecordJpaForAttendanceRepository.save(enterRecordJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(accountJpa, result.getAccountJpa()),
                () -> Assertions.assertEquals(companyJpa, result.getCompanyJpa()),
                () -> Assertions.assertEquals(enterRecord.getEnterTime(), result.getEnterTime()),
                () -> Assertions.assertEquals(enterRecord.isTardy(), result.isTardy())
        );
    }

    @Test
    @DisplayName("근무자 - 퇴근 기록 - ExitRecordJpa 저장")
    public void saveExitRecordJpaSuccess() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        LocalDateTime enterTime = LocalDateTime.of(2023, 1, 1, 12, 12, 12);
        LocalDateTime exitTime = LocalDateTime.of(2023, 1, 1, 20, 12, 12);

        // when
        AccountJpa savedAccountJpa = accountJpaForAttendanceRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForAttendanceRepository.save(companyJpa);
        EnterRecordJpa enterRecordJpa = EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .tardy(false)
                .enterTime(enterTime)
                .build();
        EnterRecordJpa savedEnterRecordJpa = enterRecordJpaForAttendanceRepository.save(enterRecordJpa);
        ExitRecordJpa exitRecordJpa = ExitRecordJpa.builder()
                .enterRecordJpa(savedEnterRecordJpa)
                .exitTime(exitTime)
                .build();
        ExitRecordJpa result = exitRecordJpaForAttendanceRepository.save(exitRecordJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(savedEnterRecordJpa, result.getEnterRecordJpa()),
                () -> Assertions.assertEquals(exitTime, result.getExitTime())
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

}