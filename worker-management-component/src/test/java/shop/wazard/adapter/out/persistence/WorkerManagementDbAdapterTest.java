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
import shop.wazard.entity.company.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
@ContextConfiguration(classes = {EntityManager.class, WorkerManagementDbAdapter.class, AccountForWorkerManagementMapper.class, WorkerManagementMapper.class, RosterJpaForWorkerManagementRepository.class, WaitingListJpaForWorkerManagementRepository.class, AccountJpaForWorkerManagementRepository.class})
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
        RosterJpa savedRosterJpa = rosterJpaForWorkerManagementRepository.save(
                RosterJpa.builder()
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
        RosterForWorkerManagement rosterDomain = workerManagementMapper.toRosterDomain(rosterJpaForWorkerManagementRepository.findRosterJpaByAccountIdAndCompanyId(savedAccountJpa.getId(), savedCompanyJpa.getId()).get());
        workerManagementMapper.updateRosterStateForExile(savedRosterJpa, rosterDomain);

        // then
        Assertions.assertEquals(savedRosterJpa.getBaseStatusJpa(), BaseEntity.BaseStatusJpa.INACTIVE);
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