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
import shop.wazard.application.domain.CommuteRecordForAttendance;
import shop.wazard.application.domain.CommuteType;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.commuteRecord.AbsentJpa;
import shop.wazard.entity.commuteRecord.CommuteRecordJpa;
import shop.wazard.entity.commuteRecord.CommuteTypeJpa;
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
        CommuteRecordJpaForAttendanceRepository.class})
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
    private CommuteRecordJpaForAttendanceRepository commuteRecordJpaForAttendanceRepository;
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
    @DisplayName("근무자 - 정상 출근 기록 - CommuteRecordJpa 저장")
    public void saveCommuteRecordJpaSuccess_ON() throws Exception {
        // given
        CommuteRecordForAttendance commuteRecordForAttendance = CommuteRecordForAttendance.builder()
                .accountId(1L)
                .companyId(2L)
                .commuteType(CommuteType.ON)
                .tardy(false)
                .commuteTime(LocalDateTime.of(2023, 1, 1, 12, 12, 12))
                .build();
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForAttendanceRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForAttendanceRepository.save(companyJpa);
        CommuteRecordJpa commuteRecordJpa = CommuteRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .commuteTypeJpa(CommuteTypeJpa.valueOf(commuteRecordForAttendance.getCommuteType().toString()))
                .tardy(commuteRecordForAttendance.isTardy())
                .commuteTime(commuteRecordForAttendance.getCommuteTime())
                .build();
        CommuteRecordJpa result = commuteRecordJpaForAttendanceRepository.save(commuteRecordJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(accountJpa, result.getAccountJpa()),
                () -> Assertions.assertEquals(companyJpa, result.getCompanyJpa()),
                () -> Assertions.assertEquals(commuteRecordForAttendance.getCommuteType().commuteType, result.getCommuteTypeJpa().commuteType),
                () -> Assertions.assertEquals(commuteRecordForAttendance.getCommuteTime(), result.getCommuteTime()),
                () -> Assertions.assertEquals(commuteRecordForAttendance.isTardy(), result.isTardy())
        );
    }

    @Test
    @DisplayName("근무자 - 지각 출근 기록 - CommuteRecordJpa 저장")
    public void saveCommuteRecordJpaSuccess_LATE() throws Exception {
        // given
        CommuteRecordForAttendance commuteRecordForAttendance = CommuteRecordForAttendance.builder()
                .accountId(1L)
                .companyId(2L)
                .commuteType(CommuteType.ON)
                .tardy(true)
                .commuteTime(LocalDateTime.of(2023, 1, 1, 12, 12, 12))
                .build();
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForAttendanceRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForAttendanceRepository.save(companyJpa);
        CommuteRecordJpa commuteRecordJpa = CommuteRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .commuteTypeJpa(CommuteTypeJpa.valueOf(commuteRecordForAttendance.getCommuteType().toString()))
                .tardy(commuteRecordForAttendance.isTardy())
                .commuteTime(commuteRecordForAttendance.getCommuteTime())
                .build();
        CommuteRecordJpa result = commuteRecordJpaForAttendanceRepository.save(commuteRecordJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(accountJpa, result.getAccountJpa()),
                () -> Assertions.assertEquals(companyJpa, result.getCompanyJpa()),
                () -> Assertions.assertEquals(commuteRecordForAttendance.getCommuteType().commuteType, result.getCommuteTypeJpa().commuteType),
                () -> Assertions.assertEquals(commuteRecordForAttendance.getCommuteTime(), result.getCommuteTime()),
                () -> Assertions.assertEquals(commuteRecordForAttendance.isTardy(), result.isTardy())
        );
    }

    @Test
    @DisplayName("근무자 - 퇴근 기록 - CommuteRecordJpa 저장")
    public void saveCommuteRecordJpaSuccess_OFF() throws Exception {
        // given
        CommuteRecordForAttendance commuteRecordForAttendance = CommuteRecordForAttendance.builder()
                .accountId(1L)
                .companyId(2L)
                .commuteType(CommuteType.OFF)
                .tardy(false)
                .commuteTime(LocalDateTime.of(2023, 1, 1, 12, 12, 12))
                .build();
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForAttendanceRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForAttendanceRepository.save(companyJpa);
        CommuteRecordJpa commuteRecordJpa = CommuteRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .commuteTypeJpa(CommuteTypeJpa.valueOf(commuteRecordForAttendance.getCommuteType().toString()))
                .tardy(commuteRecordForAttendance.isTardy())
                .commuteTime(commuteRecordForAttendance.getCommuteTime())
                .build();
        CommuteRecordJpa result = commuteRecordJpaForAttendanceRepository.save(commuteRecordJpa);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(accountJpa, result.getAccountJpa()),
                () -> Assertions.assertEquals(companyJpa, result.getCompanyJpa()),
                () -> Assertions.assertEquals(commuteRecordForAttendance.getCommuteType().commuteType, result.getCommuteTypeJpa().commuteType),
                () -> Assertions.assertEquals(commuteRecordForAttendance.getCommuteTime(), result.getCommuteTime()),
                () -> Assertions.assertEquals(commuteRecordForAttendance.isTardy(), result.isTardy())
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