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
import shop.wazard.application.domain.RosterForWorkerManagement;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.application.domain.WaitingStatus;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.commuteRecord.AbsentJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.commuteRecord.ExitRecordJpa;
import shop.wazard.entity.company.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
@ContextConfiguration(classes = {EntityManager.class,
        WorkerManagementDbAdapter.class,
        AccountForWorkerManagementMapper.class,
        WorkerManagementMapper.class,
        RosterJpaForWorkerManagementRepository.class,
        WaitingListJpaForWorkerManagementRepository.class,
        AccountJpaForWorkerManagementRepository.class,
        EnterRecordJpaForWorkerManagementRepository.class,
        AbsentRecordJpaForWorkerManagementRepository.class,
        EnterRecordJpaForWorkerManagementRepository.class})
class WorkerManagementDbAdapterTest {

    @Autowired
    private WorkerManagementMapper workerManagementMapper;
    @Autowired
    private AccountForWorkerManagementMapper accountForWorkerManagementMapper;
    @Autowired
    private RosterJpaForWorkerManagementRepository rosterJpaForWorkerManagementRepository;
    @Autowired
    private WaitingListJpaForWorkerManagementRepository waitingListJpaForWorkerManagementRepository;
    @Autowired
    private AccountJpaForWorkerManagementRepository accountJpaForWorkerManagementRepository;
    @Autowired
    private CompanyJpaForWorkerManagementRepository companyJpaForWorkerManagementRepository;
    @Autowired
    private EnterRecordJpaForWorkerManagementRepository enterRecordJpaForWorkerManagementRepository;
    @Autowired
    private AbsentRecordJpaForWorkerManagementRepository absentRecordJpaForWorkerManagementRepository;
    @Autowired
    private ExitRecordJpaForWorkerManagementRepository exitRecordJpaForWorkerManagementRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("고용주 - 업장 초대 대기목록에서 근무자 존재 유무 확인 - WaitingListJpa 조회")
    public void findWaitingInfo() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForWorkerManagementRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForWorkerManagementRepository.save(companyJpa);
        WaitingListJpa waitingListJpa = WaitingListJpa.builder()
                .companyJpa(companyJpa)
                .accountJpa(accountJpa)
                .waitingStatusJpa(WaitingStatusJpa.AGREED)
                .build();

        WaitingListJpa savedWaitingListJpa = waitingListJpaForWorkerManagementRepository.save(waitingListJpa);
        WaitingListJpa result = waitingListJpaForWorkerManagementRepository.findById(savedWaitingListJpa.getId()).get();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.getCompanyJpa(), savedWaitingListJpa.getCompanyJpa()),
                () -> Assertions.assertEquals(result.getAccountJpa(), savedWaitingListJpa.getAccountJpa()),
                () -> Assertions.assertEquals(result.getWaitingStatusJpa(), savedWaitingListJpa.getWaitingStatusJpa())
        );
    }

    @Test
    @DisplayName("고용주 - 근무자 업장 초대 시, JOINED 상태로 변경 - WaitingListJpa waitingStatus 변경")
    public void changeWaitingStatus() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        AccountJpa savedAccountJpa = accountJpaForWorkerManagementRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForWorkerManagementRepository.save(companyJpa);
        WaitingListJpa waitingListJpa = WaitingListJpa.builder()
                .companyJpa(companyJpa)
                .accountJpa(accountJpa)
                .waitingStatusJpa(WaitingStatusJpa.AGREED)
                .build();
        WaitingInfo waitingInfo = WaitingInfo.builder()
                .waitingStatus(WaitingStatus.JOINED)
                .build();

        // when
        WaitingListJpa savedWaitingListJpa = waitingListJpaForWorkerManagementRepository.save(waitingListJpa);
        savedWaitingListJpa.updateWaitingStatus(WaitingStatusJpa.valueOf(waitingInfo.getWaitingStatus().getStatus()));

        // then
        Assertions.assertEquals(savedWaitingListJpa.getWaitingStatusJpa(), WaitingStatusJpa.JOINED);
    }

    @Test
    @DisplayName("고용주 - 업장 근무자 리스트에 근무자 초대 완료 - RosterJpa 저장")
    public void joinWorker() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForWorkerManagementRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForWorkerManagementRepository.save(companyJpa);
        RosterJpa savedRosterJpa = rosterJpaForWorkerManagementRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build());

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(savedRosterJpa.getCompanyJpa(), savedCompanyJpa),
                () -> Assertions.assertEquals(savedRosterJpa.getAccountJpa(), savedAccountJpa),
                () -> Assertions.assertEquals(savedRosterJpa.getRosterTypeJpa(), RosterTypeJpa.EMPLOYEE)
        );
    }

    @Test
    @DisplayName("고용주 - 업장 소속 근무자 리스트 조회 - List<AccountJpa> 조회")
    public void getWorkersBelongedToCompanySuccess() throws Exception {
        // given
        Long companyId = setDefaultWorkerList();

        // when
        List<AccountJpa> result = accountJpaForWorkerManagementRepository.findWorkersBelongedToCompany(companyId);
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals("test1@email.com", result.get(0).getEmail()),
                () -> Assertions.assertEquals("test2@email.com", result.get(1).getEmail()),
                () -> Assertions.assertEquals("test3@email.com", result.get(2).getEmail())
        );
    }

    @Test
    @DisplayName("고용주 - 추방을 위한 알바생 조회 - 성공")
    void findWorkerForExile() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForWorkerManagementRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForWorkerManagementRepository.save(companyJpa);
        RosterJpa rosterJpa = setDefaultRosterJpa(savedAccountJpa, savedCompanyJpa);
        RosterJpa savedRosterJpa = rosterJpaForWorkerManagementRepository.save(rosterJpa);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(savedRosterJpa.getAccountJpa(), savedAccountJpa),
                () -> Assertions.assertEquals(savedRosterJpa.getCompanyJpa(), savedCompanyJpa)
        );
    }

    @Test
    @DisplayName("고용주 - 알바생 추방(상태값 변경) - 성공")
    void exileWorker() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForWorkerManagementRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForWorkerManagementRepository.save(companyJpa);
        RosterJpa rosterJpa = setDefaultRosterJpa(savedAccountJpa, savedCompanyJpa);
        RosterJpa savedRosterJpa = rosterJpaForWorkerManagementRepository.save(rosterJpa);
        RosterForWorkerManagement rosterForWorkerManagement = workerManagementMapper.toRosterDomain(savedRosterJpa);
        workerManagementMapper.updateRosterStateForExile(savedRosterJpa, rosterForWorkerManagement);
        RosterJpa findRosterJpa = rosterJpaForWorkerManagementRepository.findRosterJpaByAccountIdAndCompanyId(savedAccountJpa.getId(), savedCompanyJpa.getId()).get();

        // then
        Assertions.assertEquals(findRosterJpa.getBaseStatusJpa(), savedRosterJpa.getBaseStatusJpa());
    }

    @Test
    @DisplayName("고용주 - 업장 초대 대기자 목록 조회 - List<WaitingListJpa> 조회")
    public void getWaitingWorker() throws Exception {
        // given
        List<AccountJpa> savedAccountJpaList = setDefaultAccountJpaForGetWaitingWorker();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        CompanyJpa savedCompanyJpa = companyJpaForWorkerManagementRepository.save(companyJpa);
        List<WaitingListJpa> savedWaitingListJpaList = saveWaitingListJpa(savedAccountJpaList, savedCompanyJpa);
        em.flush();

        List<WaitingListJpa> result = waitingListJpaForWorkerManagementRepository.findWaitingWorkers(savedCompanyJpa.getId());

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(3, result.size()),  // 이미 가입이 완료된 근무자는 조회되지 않아야 함

                () -> Assertions.assertEquals(savedWaitingListJpaList.get(0).getAccountJpa().getId(), result.get(0).getAccountJpa().getId()),
                () -> Assertions.assertEquals(savedWaitingListJpaList.get(0).getAccountJpa().getEmail(), result.get(0).getAccountJpa().getEmail()),
                () -> Assertions.assertEquals(savedWaitingListJpaList.get(0).getAccountJpa().getUserName(), result.get(0).getAccountJpa().getUserName()),
                () -> Assertions.assertEquals(savedWaitingListJpaList.get(0).getWaitingStatusJpa(), result.get(0).getWaitingStatusJpa()),

                () -> Assertions.assertEquals(savedWaitingListJpaList.get(1).getAccountJpa().getId(), result.get(1).getAccountJpa().getId()),
                () -> Assertions.assertEquals(savedWaitingListJpaList.get(1).getAccountJpa().getEmail(), result.get(1).getAccountJpa().getEmail()),
                () -> Assertions.assertEquals(savedWaitingListJpaList.get(1).getAccountJpa().getUserName(), result.get(1).getAccountJpa().getUserName()),
                () -> Assertions.assertEquals(savedWaitingListJpaList.get(1).getWaitingStatusJpa(), result.get(1).getWaitingStatusJpa()),

                () -> Assertions.assertEquals(savedWaitingListJpaList.get(2).getAccountJpa().getId(), result.get(2).getAccountJpa().getId()),
                () -> Assertions.assertEquals(savedWaitingListJpaList.get(2).getAccountJpa().getEmail(), result.get(2).getAccountJpa().getEmail()),
                () -> Assertions.assertEquals(savedWaitingListJpaList.get(2).getAccountJpa().getUserName(), result.get(2).getAccountJpa().getUserName()),
                () -> Assertions.assertEquals(savedWaitingListJpaList.get(2).getWaitingStatusJpa(), result.get(2).getWaitingStatusJpa())
        );
    }

    @Test
    @DisplayName("고용주 - 특정 근무자 월별 출/퇴근 기록 조회 - List<EnterRecordJpa> 조회" +
            "퇴근을 아직 하지 않은 경우 출근 시간만 조회, 특정 근무자 + YYYY년 MM월 특정 기간에 속한 출퇴근 기록만 조회")
    public void findAllRecordOfWorkerSuccess() throws Exception {
        // given
        List<AccountJpa> accountJpaList = setDefaultAccountJpaForGetWorkerAttendanceRecord();
        AccountJpa savedAccountJpa1 = accountJpaList.get(0);
        AccountJpa savedAccountJpa2 = accountJpaList.get(1);

        CompanyJpa savedCompanyJpa = companyJpaForWorkerManagementRepository.save(setDefaultCompanyJpa());

        List<LocalDate> enterDateList = setDefaultEnterDate();
        List<LocalDate> exitDateList = setDefaultExitDate();

        // account1 출/퇴근 기록 저장
        List<EnterRecordJpa> enterRecordJpaList = setEnterRecordForAccount1(savedAccountJpa1, savedCompanyJpa, enterDateList);
        EnterRecordJpa savedEnterRecordJpa_5_1_forAccount1 = enterRecordJpaList.get(0);
        EnterRecordJpa savedEnterRecordJpa_5_20_forAccount1 = enterRecordJpaList.get(1);
        EnterRecordJpa savedEnterRecordJpa_4_30_forAccount1 = enterRecordJpaList.get(2);  // 조회되지 않아야 함

        List<ExitRecordJpa> exitRecordJpaList = setExitRecordForAccount1(enterRecordJpaList, exitDateList);
        ExitRecordJpa savedExitRecordJpa_5_1_forAccount1 = exitRecordJpaList.get(0);
        ExitRecordJpa savedExitRecordJpa_4_30_forAccount1 = exitRecordJpaList.get(1);  // 조회되지 않아야 함


        // account2 출/퇴근 기록 - 조회되지 않아야 함
        EnterRecordJpa savedEnterRecordJpa1_forAccount2 = setEnterRecordForAccount2(savedAccountJpa2, savedCompanyJpa, enterDateList);
        ExitRecordJpa exitRecordJpa1_forAccount2 = setExitRecordForAccount2(savedEnterRecordJpa1_forAccount2, exitDateList);

        em.flush();
        em.clear();

        // when
        LocalDate STARTDATE = LocalDate.of(2023, 5, 1);
        LocalDate ENDDATE = LocalDate.of(2023, 6, 1);
        List<EnterRecordJpa> result = enterRecordJpaForWorkerManagementRepository.findAllRecordOfWorker(savedAccountJpa1.getId(), savedCompanyJpa.getId(), STARTDATE, ENDDATE);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, result.size()),  // 5월에 특정 알바생의 출/퇴근 기록을 조회함

                // 첫번째 출근 & 퇴근 기록
                () -> Assertions.assertEquals(savedEnterRecordJpa_5_1_forAccount1.getAccountJpa().getId(), result.get(0).getAccountJpa().getId()),
                () -> Assertions.assertEquals(savedEnterRecordJpa_5_1_forAccount1.getEnterDate(), result.get(0).getEnterDate()),
                () -> Assertions.assertEquals(savedEnterRecordJpa_5_1_forAccount1.getEnterTime(), result.get(0).getEnterTime()),
                () -> Assertions.assertEquals(savedEnterRecordJpa_5_1_forAccount1.isTardy(), result.get(0).isTardy()),

                () -> Assertions.assertEquals(savedExitRecordJpa_5_1_forAccount1.getEnterRecordJpa().getId(), result.get(0).getId()),
                () -> Assertions.assertEquals(savedExitRecordJpa_5_1_forAccount1.getExitDate(), result.get(0).getExitRecordJpa().getExitDate()),
                () -> Assertions.assertEquals(savedExitRecordJpa_5_1_forAccount1.getExitTime(), result.get(0).getExitRecordJpa().getExitTime()),

                // 두번째 출근 기록(퇴근을 아직 안한 경우)
                () -> Assertions.assertEquals(savedEnterRecordJpa_5_20_forAccount1.getAccountJpa().getId(), result.get(1).getAccountJpa().getId()),
                () -> Assertions.assertEquals(savedEnterRecordJpa_5_20_forAccount1.getEnterDate(), result.get(1).getEnterDate()),
                () -> Assertions.assertEquals(savedEnterRecordJpa_5_20_forAccount1.getEnterTime(), result.get(1).getEnterTime()),
                () -> Assertions.assertEquals(savedEnterRecordJpa_5_20_forAccount1.isTardy(), result.get(1).isTardy()),

                () -> Assertions.assertNull(result.get(1).getExitRecordJpa())  // 퇴근을 아직 하지 않았기 때문에 조회되지 않아야 함
        );
    }

    @Test
    @DisplayName("고용주 - 특정 근무자 결석 기록 조회 - List<AbsentJpa> 조회")
    public void findAllAbsentRecordOfWorkerSuccess() throws Exception {
        // given
        List<AccountJpa> accountJpaList = setDefaultAccountJpaForGetWorkerAttendanceRecord();
        AccountJpa savedAccountJpa1 = accountJpaList.get(0);
        AccountJpa savedAccountJpa2 = accountJpaList.get(1);

        CompanyJpa savedCompanyJpa = companyJpaForWorkerManagementRepository.save(setDefaultCompanyJpa());

        List<LocalDate> absentDateList = setDefaultAbsentDate();

        // account1 결석 기록
        AbsentJpa absentJpa_5_1_forAccount1 = AbsentJpa.builder()
                .accountJpa(savedAccountJpa1)
                .companyJpa(savedCompanyJpa)
                .absentDate(absentDateList.get(0))
                .build();
        AbsentJpa absentJpa_5_20_forAccount1 = AbsentJpa.builder()
                .accountJpa(savedAccountJpa1)
                .companyJpa(savedCompanyJpa)
                .absentDate(absentDateList.get(1))
                .build();
        AbsentJpa absentJpa_4_30_forAccount1 = AbsentJpa.builder()
                .accountJpa(savedAccountJpa1)
                .companyJpa(savedCompanyJpa)
                .absentDate(absentDateList.get(2))
                .build();

        // account2 결석 기록 -> 조회되지 않아야 함
        AbsentJpa absentJpa_5_1_forAccount2 = AbsentJpa.builder()
                .accountJpa(savedAccountJpa2)
                .companyJpa(savedCompanyJpa)
                .absentDate(absentDateList.get(0))
                .build();

        AbsentJpa savedAbsentJpa_5_1_forAccount1 = absentRecordJpaForWorkerManagementRepository.save(absentJpa_5_1_forAccount1);
        AbsentJpa savedAbsentJpa_5_20_forAccount1 = absentRecordJpaForWorkerManagementRepository.save(absentJpa_5_20_forAccount1);
        AbsentJpa savedAbsentJpa_4_30_forAccount1 = absentRecordJpaForWorkerManagementRepository.save(absentJpa_4_30_forAccount1);
        AbsentJpa savedAbsentJpa_5_1_forAccount2 = absentRecordJpaForWorkerManagementRepository.save(absentJpa_5_1_forAccount2);

        em.flush();
        em.clear();

        // when
        LocalDate STARTDATE = LocalDate.of(2023, 5, 1);
        LocalDate ENDDATE = LocalDate.of(2023, 6, 1);
        List<AbsentJpa> result = absentRecordJpaForWorkerManagementRepository.findAllAbsentRecordOfWorker(savedAccountJpa1.getId(), savedCompanyJpa.getId(), STARTDATE, ENDDATE);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(2, result.size()),

                () -> Assertions.assertEquals(savedAccountJpa1.getId(), result.get(0).getAccountJpa().getId()),
                () -> Assertions.assertEquals(savedCompanyJpa.getId(), result.get(0).getCompanyJpa().getId()),
                () -> Assertions.assertEquals(savedAbsentJpa_5_1_forAccount1.getAbsentDate(), result.get(0).getAbsentDate()),

                () -> Assertions.assertEquals(savedAccountJpa1.getId(), result.get(1).getAccountJpa().getId()),
                () -> Assertions.assertEquals(savedCompanyJpa.getId(), result.get(1).getCompanyJpa().getId()),
                () -> Assertions.assertEquals(savedAbsentJpa_5_20_forAccount1.getAbsentDate(), result.get(1).getAbsentDate())
        );
    }

    private List<LocalDate> setDefaultAbsentDate() {
        LocalDate ABSENTDATE_5_1 = LocalDate.of(2023, 5, 1);
        LocalDate ABSENTDATE_5_20 = LocalDate.of(2023, 5, 20);
        LocalDate ABSENTDATE_4_30 = LocalDate.of(2023, 4, 30);

        List<LocalDate> absentDateList = new ArrayList<>();
        absentDateList.add(ABSENTDATE_5_1);
        absentDateList.add(ABSENTDATE_5_20);
        absentDateList.add(ABSENTDATE_4_30);

        return absentDateList;
    }

    private EnterRecordJpa setEnterRecordForAccount2(AccountJpa savedAccountJpa2, CompanyJpa savedCompanyJpa, List<LocalDate> enterDateList) {
        LocalDate ENTERDATE_5_1 = enterDateList.get(0);
        LocalDate ENTERDATE_5_20 = enterDateList.get(1);
        LocalDate ENTERDATE_4_30 = enterDateList.get(2);

        EnterRecordJpa enterRecordJpa1_ForAccount2 = EnterRecordJpa.builder()
                .accountJpa(savedAccountJpa2)
                .companyJpa(savedCompanyJpa)
                .tardy(false)
                .enterDate(ENTERDATE_5_1)
                .enterTime(LocalDateTime.now())
                .build();

        return enterRecordJpaForWorkerManagementRepository.save(enterRecordJpa1_ForAccount2);
    }

    private ExitRecordJpa setExitRecordForAccount2(EnterRecordJpa savedEnterRecordJpa1_forAccount2, List<LocalDate> exitDateList) {
        LocalDate EXITDATE_5_1 = exitDateList.get(0);
        LocalDate EXITDATE_5_21 = exitDateList.get(1);
        LocalDate EXITDATE_4_30 = exitDateList.get(2);

        ExitRecordJpa exitRecordJpa1_forAccount2 = ExitRecordJpa.builder()
                .enterRecordJpa(savedEnterRecordJpa1_forAccount2)
                .exitDate(EXITDATE_5_1)
                .exitTime(LocalDateTime.now())
                .build();

        return exitRecordJpaForWorkerManagementRepository.save(exitRecordJpa1_forAccount2);
    }

    private List<EnterRecordJpa> setEnterRecordForAccount1(AccountJpa savedAccountJpa1, CompanyJpa savedCompanyJpa, List<LocalDate> enterDateList) {
        LocalDate ENTERDATE_5_1 = enterDateList.get(0);
        LocalDate ENTERDATE_5_20 = enterDateList.get(1);
        LocalDate ENTERDATE_4_30 = enterDateList.get(2);

        EnterRecordJpa enterRecordJpa1_forAccount1 = EnterRecordJpa.builder()
                .accountJpa(savedAccountJpa1)
                .companyJpa(savedCompanyJpa)
                .tardy(false)
                .enterDate(ENTERDATE_5_1)
                .enterTime(LocalDateTime.now())
                .build();
        EnterRecordJpa enterRecordJpa2_forAccount1 = EnterRecordJpa.builder()
                .accountJpa(savedAccountJpa1)
                .companyJpa(savedCompanyJpa)
                .tardy(true)
                .enterDate(ENTERDATE_5_20)
                .enterTime(LocalDateTime.now())
                .build();
        EnterRecordJpa enterRecordJpa3_forAccount1 = EnterRecordJpa.builder()
                .accountJpa(savedAccountJpa1)
                .companyJpa(savedCompanyJpa)
                .tardy(false)
                .enterDate(ENTERDATE_4_30)
                .enterTime(LocalDateTime.now())
                .build();

        EnterRecordJpa savedEnterRecordJpa_5_1_forAccount1 = enterRecordJpaForWorkerManagementRepository.save(enterRecordJpa1_forAccount1);
        EnterRecordJpa savedEnterRecordJpa_5_20_forAccount1 = enterRecordJpaForWorkerManagementRepository.save(enterRecordJpa2_forAccount1);
        EnterRecordJpa savedEnterRecordJpa_4_30_forAccount1 = enterRecordJpaForWorkerManagementRepository.save(enterRecordJpa3_forAccount1);

        List<EnterRecordJpa> enterRecordJpaList = new ArrayList<>();
        enterRecordJpaList.add(savedEnterRecordJpa_5_1_forAccount1);
        enterRecordJpaList.add(savedEnterRecordJpa_5_20_forAccount1);
        enterRecordJpaList.add(savedEnterRecordJpa_4_30_forAccount1);

        return enterRecordJpaList;
    }

    private List<ExitRecordJpa> setExitRecordForAccount1(List<EnterRecordJpa> enterRecordJpaList, List<LocalDate> exitDateList) {
        LocalDate EXITDATE_5_1 = exitDateList.get(0);
        LocalDate EXITDATE_5_21 = exitDateList.get(1);
        LocalDate EXITDATE_4_30 = exitDateList.get(2);

        EnterRecordJpa savedEnterRecordJpa_5_1_forAccount1 = enterRecordJpaList.get(0);
        EnterRecordJpa savedEnterRecordJpa_5_20_forAccount1 = enterRecordJpaList.get(0);
        EnterRecordJpa savedEnterRecordJpa_4_30_forAccount1 = enterRecordJpaList.get(2);

        ExitRecordJpa exitRecordJpa1_forAccount1 = ExitRecordJpa.builder()
                .enterRecordJpa(savedEnterRecordJpa_5_1_forAccount1)
                .exitDate(EXITDATE_5_1)
                .exitTime(LocalDateTime.now())
                .build();
        ExitRecordJpa exitRecordJpa2_forAccount1 = ExitRecordJpa.builder()
                .enterRecordJpa(savedEnterRecordJpa_4_30_forAccount1)
                .exitDate(EXITDATE_4_30)
                .exitTime(LocalDateTime.now())
                .build();

        ExitRecordJpa savedExitRecordJpa_5_1_forAccount1 = exitRecordJpaForWorkerManagementRepository.save(exitRecordJpa1_forAccount1);
        ExitRecordJpa savedExitRecordJpa_4_30_forAccount1 = exitRecordJpaForWorkerManagementRepository.save(exitRecordJpa2_forAccount1);

        List<ExitRecordJpa> exitRecordJpaList = new ArrayList<>();
        exitRecordJpaList.add(savedExitRecordJpa_5_1_forAccount1);
        exitRecordJpaList.add(savedExitRecordJpa_4_30_forAccount1);

        return exitRecordJpaList;
    }

    private List<LocalDate> setDefaultEnterDate() {
        LocalDate ENTERDATE_5_1 = LocalDate.of(2023, 5, 1);
        LocalDate ENTERDATE_5_20 = LocalDate.of(2023, 5, 20);
        LocalDate ENTERDATE_4_30 = LocalDate.of(2023, 4, 30);

        List<LocalDate> enterDateList = new ArrayList<>();
        enterDateList.add(ENTERDATE_5_1);
        enterDateList.add(ENTERDATE_5_20);
        enterDateList.add(ENTERDATE_4_30);

        return enterDateList;
    }

    private List<LocalDate> setDefaultExitDate() {
        LocalDate EXITDATE_5_1 = LocalDate.of(2023, 5, 1);
        LocalDate EXITDATE_5_20 = LocalDate.of(2023, 5, 21);
        LocalDate EXITDATE_4_30 = LocalDate.of(2023, 4, 30);

        List<LocalDate> exitDateList = new ArrayList<>();
        exitDateList.add(EXITDATE_5_1);
        exitDateList.add(EXITDATE_5_20);
        exitDateList.add(EXITDATE_4_30);

        return exitDateList;
    }

    private List<AccountJpa> setDefaultAccountJpaForGetWorkerAttendanceRecord() {
        List<AccountJpa> accountJpaList = new ArrayList<>();
        AccountJpa accountJpa1 = AccountJpa.builder()
                .email("test1@email.com")
                .password("testPwd")
                .userName("testName1")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        AccountJpa accountJpa2 = AccountJpa.builder()
                .email("test2@email.com")
                .password("testPwd")
                .userName("testName2")
                .phoneNumber("010-2222-2222")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();

        AccountJpa savedAccountJpa1 = accountJpaForWorkerManagementRepository.save(accountJpa1);
        AccountJpa savedAccountJpa2 = accountJpaForWorkerManagementRepository.save(accountJpa2);

        accountJpaList.add(savedAccountJpa1);
        accountJpaList.add(savedAccountJpa2);

        return accountJpaList;
    }

    private List<WaitingListJpa> saveWaitingListJpa(List<AccountJpa> savedAccountJpaList, CompanyJpa savedCompanyJpa) {
        WaitingListJpa waitingListJpa1 = WaitingListJpa.builder()
                .accountJpa(savedAccountJpaList.get(0))
                .companyJpa(savedCompanyJpa)
                .waitingStatusJpa(WaitingStatusJpa.AGREED)
                .build();
        WaitingListJpa waitingListJpa2 = WaitingListJpa.builder()
                .accountJpa(savedAccountJpaList.get(1))
                .companyJpa(savedCompanyJpa)
                .waitingStatusJpa(WaitingStatusJpa.INVITED)
                .build();
        WaitingListJpa waitingListJpa3 = WaitingListJpa.builder()
                .accountJpa(savedAccountJpaList.get(2))
                .companyJpa(savedCompanyJpa)
                .waitingStatusJpa(WaitingStatusJpa.DISAGREED)
                .build();
        WaitingListJpa waitingListJpa4 = WaitingListJpa.builder()  // 이미 업장에 가입된 근무자 정보는 조회되지 않아야 함
                .accountJpa(savedAccountJpaList.get(3))
                .companyJpa(savedCompanyJpa)
                .waitingStatusJpa(WaitingStatusJpa.JOINED)
                .build();

        WaitingListJpa savedWaitingListJpa1 = waitingListJpaForWorkerManagementRepository.save(waitingListJpa1);
        WaitingListJpa savedWaitingListJpa2 = waitingListJpaForWorkerManagementRepository.save(waitingListJpa2);
        WaitingListJpa savedWaitingListJpa3 = waitingListJpaForWorkerManagementRepository.save(waitingListJpa3);
        WaitingListJpa savedWaitingListJpa4 = waitingListJpaForWorkerManagementRepository.save(waitingListJpa4);

        List<WaitingListJpa> savedWaitingListJpaList = new ArrayList<>();
        savedWaitingListJpaList.add(savedWaitingListJpa1);
        savedWaitingListJpaList.add(savedWaitingListJpa2);
        savedWaitingListJpaList.add(savedWaitingListJpa3);
        savedWaitingListJpaList.add(savedWaitingListJpa4);

        return savedWaitingListJpaList;
    }

    private List<AccountJpa> setDefaultAccountJpaForGetWaitingWorker() {
        AccountJpa accountJpa1 = AccountJpa.builder()
                .email("test1@email.com")
                .password("testPwd")
                .userName("testName1")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        AccountJpa accountJpa2 = AccountJpa.builder()
                .email("test2@email.com")
                .password("testPwd")
                .userName("testName2")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        AccountJpa accountJpa3 = AccountJpa.builder()
                .email("test3@email.com")
                .password("testPwd")
                .userName("testName2")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        AccountJpa accountJpa4 = AccountJpa.builder()
                .email("test4@email.com")
                .password("testPwd")
                .userName("testName2")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();

        AccountJpa savedAccountJpa1 = accountJpaForWorkerManagementRepository.save(accountJpa1);
        AccountJpa savedAccountJpa2 = accountJpaForWorkerManagementRepository.save(accountJpa2);
        AccountJpa savedAccountJpa3 = accountJpaForWorkerManagementRepository.save(accountJpa3);
        AccountJpa savedAccountJpa4 = accountJpaForWorkerManagementRepository.save(accountJpa4);

        List<AccountJpa> savedAccountJpaList = new ArrayList<>();
        savedAccountJpaList.add(savedAccountJpa1);
        savedAccountJpaList.add(savedAccountJpa2);
        savedAccountJpaList.add(savedAccountJpa3);
        savedAccountJpaList.add(savedAccountJpa4);

        return savedAccountJpaList;
    }

    private RosterJpa setDefaultRosterJpa(AccountJpa accountJpa, CompanyJpa companyJpa) {
        return RosterJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build();
    }

    private Long setDefaultWorkerList() {
        CompanyJpa companyJpa = setDefaultCompanyJpa();
        AccountJpa accountJpa1 = AccountJpa.builder()
                .email("test1@email.com")
                .password("testPwd")
                .userName("testName")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        AccountJpa accountJpa2 = AccountJpa.builder()
                .email("test2@email.com")
                .password("testPwd")
                .userName("testName")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();
        AccountJpa accountJpa3 = AccountJpa.builder()
                .email("test3@email.com")
                .password("testPwd")
                .userName("testName")
                .phoneNumber("010-1111-1111")
                .gender(GenderTypeJpa.MALE.getGender())
                .birth(LocalDate.of(2023, 1, 1))
                .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                .roles("EMPLOYEE")
                .build();

        CompanyJpa savedCompanyJpa = companyJpaForWorkerManagementRepository.save(companyJpa);
        AccountJpa savedAccountJpa1 = accountJpaForWorkerManagementRepository.save(accountJpa1);
        AccountJpa savedAccountJpa2 = accountJpaForWorkerManagementRepository.save(accountJpa2);
        AccountJpa savedAccountJpa3 = accountJpaForWorkerManagementRepository.save(accountJpa3);

        rosterJpaForWorkerManagementRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa1)
                .companyJpa(savedCompanyJpa)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build());
        rosterJpaForWorkerManagementRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa2)
                .companyJpa(savedCompanyJpa)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build());
        rosterJpaForWorkerManagementRepository.save(RosterJpa.builder()
                .accountJpa(savedAccountJpa3)
                .companyJpa(savedCompanyJpa)
                .rosterTypeJpa(RosterTypeJpa.EMPLOYEE)
                .build());
        em.flush();
        return savedCompanyJpa.getId();
    }

    private AccountJpa setDefaultAccountJpa() {
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