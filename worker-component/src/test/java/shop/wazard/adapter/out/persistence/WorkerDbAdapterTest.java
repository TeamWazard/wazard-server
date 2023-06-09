package shop.wazard.adapter.out.persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
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
import shop.wazard.application.domain.ContractInfo;
import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.WaitingListJpa;
import shop.wazard.entity.company.WaitingStatusJpa;
import shop.wazard.entity.contract.ContractJpa;
import shop.wazard.entity.worker.ReplaceWorkerJpa;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
@ContextConfiguration(
        classes = {
            EntityManager.class,
            WorkerDbAdapter.class,
            WorkerMapper.class,
            AccountForWorkerMapper.class,
            WaitingListForWorkerMapper.class,
            ContractInfoForWorkerMapper.class,
            AccountJpaForWorkerRepository.class,
            CompanyJpaForWorkerRepository.class,
            ReplaceJpaForWorkerRepository.class,
            ContractJpaForWorkerRepository.class,
            WaitingListJpaForWorkerRepository.class
        })
class WorkerDbAdapterTest {

    @Autowired private WorkerMapper workerMapper;
    @Autowired private AccountForWorkerMapper accountForWorkerMapper;
    @Autowired private ContractInfoForWorkerMapper contractInfoForWorkerMapper;
    @Autowired private WaitingListForWorkerMapper waitingListForWorkerMapper;
    @Autowired private AccountJpaForWorkerRepository accountJpaForWorkerRepository;
    @Autowired private CompanyJpaForWorkerRepository companyJpaForWorkerRepository;
    @Autowired private ReplaceJpaForWorkerRepository replaceJpaForWorkerRepository;
    @Autowired private ContractJpaForWorkerRepository contractJpaForWorkerRepository;
    @Autowired private WaitingListJpaForWorkerRepository waitingListJpaForWorkerRepository;
    @Autowired private EntityManager em;

    @Test
    @DisplayName("근무자 - 초기 계약정보 동의/비동의에서 계약정보 조회 - ContractJpa 조회")
    void findContractInfoByContractId() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForWorkerRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForWorkerRepository.save(companyJpa);
        ContractJpa contractJpa =
                ContractJpa.builder()
                        .accountJpa(savedAccountJpa)
                        .companyJpa(savedCompanyJpa)
                        .contractInfoAgreement(false)
                        .build();
        ContractJpa savedContractJpa = contractJpaForWorkerRepository.save(contractJpa);
        ContractJpa findContractJpa =
                contractJpaForWorkerRepository.findById(savedContractJpa.getId()).get();
        ContractInfo contractInfo =
                contractInfoForWorkerMapper.contractJpaToContractInfo(savedContractJpa);

        // then
        Assertions.assertEquals(
                contractInfo.isContractInfoAgreement(), findContractJpa.isContractInfoAgreement());
    }

    @Test
    @DisplayName("근무자 - 초기 계약정보 동의/비동의에서 동의 여부 변경 및 입력 - ContractJpa 수정")
    void modifyContractAgreement() throws Exception {
        // given
        ContractInfo contractInfo = ContractInfo.builder().contractInfoAgreement(true).build();
        ContractJpa contractJpa = ContractJpa.builder().contractInfoAgreement(false).build();

        // when
        ContractJpa savedContractJpa = contractJpaForWorkerRepository.save(contractJpa);
        contractInfoForWorkerMapper.modifyContractAgreement(savedContractJpa, contractInfo);

        // then
        Assertions.assertEquals(
                contractInfo.isContractInfoAgreement(), savedContractJpa.isContractInfoAgreement());
    }

    @Test
    @DisplayName("근무자 - 초기 계약정보 동의/비동의에서 대기자 목록 변경 - WaitingListJpa 수정")
    void modifyWaitingListState() throws Exception {
        // given
        List<ContractInfo> contractInfoList = setDefaultContractInfoList();
        List<AccountJpa> accountJpaList = setDefaultEmployeeAccountJpaList();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        CompanyJpa savedCompanyJpa = companyJpaForWorkerRepository.save(companyJpa);
        List<WaitingListJpa> waitingListJpaList =
                setDefaultListOfWaitingList(accountJpaList, savedCompanyJpa);
        waitingListForWorkerMapper.modifyWaitingListState(
                waitingListJpaList.get(0), contractInfoList.get(0));
        waitingListForWorkerMapper.modifyWaitingListState(
                waitingListJpaList.get(1), contractInfoList.get(1));

        // then
        Assertions.assertAll(
                () ->
                        Assertions.assertEquals(
                                WaitingStatusJpa.AGREED,
                                waitingListJpaList.get(0).getWaitingStatusJpa()),
                () ->
                        Assertions.assertEquals(
                                WaitingStatusJpa.DISAGREED,
                                waitingListJpaList.get(1).getWaitingStatusJpa()));
    }

    @Test
    @DisplayName("근무자 - 대타 등록 - ReplaceWorkerJpa 저장")
    public void registerReplaceSuccess() throws Exception {
        // given

        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForWorkerRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForWorkerRepository.save(companyJpa);
        ReplaceInfo replaceInfo =
                ReplaceInfo.builder()
                        .companyId(savedCompanyJpa.getId())
                        .replaceWorkerName("test1")
                        .replaceDate(LocalDate.now())
                        .enterTime(LocalDateTime.now())
                        .exitTime(LocalDateTime.now())
                        .build();
        ReplaceWorkerJpa result =
                replaceJpaForWorkerRepository.save(
                        workerMapper.saveReplaceInfo(
                                savedAccountJpa, savedCompanyJpa, replaceInfo));
        em.flush();

        // then
        Assertions.assertAll(
                () ->
                        Assertions.assertEquals(
                                savedAccountJpa.getId(), result.getAccountJpa().getId()),
                () ->
                        Assertions.assertEquals(
                                savedCompanyJpa.getId(), result.getCompanyJpa().getId()),
                () ->
                        Assertions.assertEquals(
                                replaceInfo.getReplaceWorkerName(), result.getReplaceWorkerName()),
                () ->
                        Assertions.assertEquals(
                                replaceInfo.getReplaceDate(), result.getReplaceDate()),
                () -> Assertions.assertEquals(replaceInfo.getEnterTime(), result.getEnterTime()),
                () -> Assertions.assertEquals(replaceInfo.getExitTime(), result.getExitTime()));
    }

    @Test
    @DisplayName("근무자 - 대타기록 조회 - List<RepalceWorkerJpa> 조회")
    public void getMyReplaceRecordSuccess() throws Exception {

        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForWorkerRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForWorkerRepository.save(companyJpa);
        List<ReplaceWorkerJpa> replaceWorkerJpaList =
                setDefaultReplaceWorkerJpaList(savedAccountJpa, savedCompanyJpa);
        em.flush();
        em.clear();
        List<ReplaceWorkerJpa> result =
                replaceJpaForWorkerRepository.findMyReplaceRecord(
                        savedCompanyJpa.getId(), savedAccountJpa.getId());

        // then
        Assertions.assertAll(
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(0).getAccountJpa().getId(),
                                result.get(0).getAccountJpa().getId()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(0).getCompanyJpa().getId(),
                                result.get(0).getCompanyJpa().getId()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(0).getReplaceWorkerName(),
                                result.get(0).getReplaceWorkerName()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(0).getReplaceDate(),
                                result.get(0).getReplaceDate()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(0).getEnterTime(),
                                result.get(0).getEnterTime()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(0).getExitTime(),
                                result.get(0).getExitTime()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(1).getAccountJpa().getId(),
                                result.get(1).getAccountJpa().getId()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(1).getCompanyJpa().getId(),
                                result.get(1).getCompanyJpa().getId()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(1).getReplaceWorkerName(),
                                result.get(1).getReplaceWorkerName()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(1).getReplaceDate(),
                                result.get(1).getReplaceDate()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(1).getEnterTime(),
                                result.get(1).getEnterTime()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(1).getExitTime(),
                                result.get(1).getExitTime()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(2).getAccountJpa().getId(),
                                result.get(2).getAccountJpa().getId()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(2).getCompanyJpa().getId(),
                                result.get(2).getCompanyJpa().getId()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(2).getReplaceWorkerName(),
                                result.get(2).getReplaceWorkerName()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(2).getReplaceDate(),
                                result.get(2).getReplaceDate()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(2).getEnterTime(),
                                result.get(2).getEnterTime()),
                () ->
                        Assertions.assertEquals(
                                replaceWorkerJpaList.get(2).getExitTime(),
                                result.get(2).getExitTime()));
    }

    @Test
    @DisplayName("근무자 - 초기 계약정보 조회 - ContractJpa조회")
    public void getEarlyContractInfoSuccess() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForWorkerRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForWorkerRepository.save(companyJpa);
        ContractJpa contractJpa = setDefaultContractJpa(savedAccountJpa, savedCompanyJpa);
        ContractJpa savedContractJpa = contractJpaForWorkerRepository.save(contractJpa);
        em.flush();
        em.clear();
        ContractJpa result =
                contractJpaForWorkerRepository
                        .findByInviteCode(savedContractJpa.getInviteCode())
                        .get();

        // then
        Assertions.assertAll(
                () ->
                        Assertions.assertEquals(
                                contractJpa.getAccountJpa().getId(),
                                result.getAccountJpa().getId()),
                () ->
                        Assertions.assertEquals(
                                contractJpa.getCompanyJpa().getId(),
                                result.getCompanyJpa().getId()),
                () -> Assertions.assertEquals(contractJpa.getInviteCode(), result.getInviteCode()),
                () ->
                        Assertions.assertEquals(
                                contractJpa.getStartPeriod(), result.getStartPeriod()),
                () -> Assertions.assertEquals(contractJpa.getEndPeriod(), result.getEndPeriod()),
                () -> Assertions.assertEquals(contractJpa.getWorkTime(), result.getWorkTime()),
                () -> Assertions.assertEquals(contractJpa.getWage(), result.getWage()),
                () ->
                        Assertions.assertEquals(
                                contractJpa.isContractInfoAgreement(),
                                result.isContractInfoAgreement()));
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
                .zipCode(100)
                .companyAddress("companyAddress")
                .companyDetailAddress("companyDetailAddress")
                .companyContact("02-111-1111")
                .salaryDate(1)
                .businessType("type")
                .logoImageUrl("www.test.com")
                .build();
    }

    private List<ReplaceWorkerJpa> setDefaultReplaceWorkerJpaList(
            AccountJpa accountJpa, CompanyJpa companyJpa) {
        List<ReplaceWorkerJpa> replaceWorkerJpaList = new ArrayList<>();
        ReplaceWorkerJpa replaceWorkerJpa1 =
                ReplaceWorkerJpa.builder()
                        .accountJpa(accountJpa)
                        .companyJpa(companyJpa)
                        .replaceWorkerName("test1")
                        .replaceDate(LocalDate.of(2023, 5, 29))
                        .enterTime(LocalDateTime.of(2023, 5, 29, 18, 0))
                        .exitTime(LocalDateTime.of(2023, 5, 29, 20, 0))
                        .build();
        ReplaceWorkerJpa replaceWorkerJpa2 =
                ReplaceWorkerJpa.builder()
                        .accountJpa(accountJpa)
                        .companyJpa(companyJpa)
                        .replaceWorkerName("test1")
                        .replaceDate(LocalDate.of(2023, 5, 29))
                        .enterTime(LocalDateTime.of(2023, 5, 29, 18, 0))
                        .exitTime(LocalDateTime.of(2023, 5, 29, 20, 0))
                        .build();
        ReplaceWorkerJpa replaceWorkerJpa3 =
                ReplaceWorkerJpa.builder()
                        .accountJpa(accountJpa)
                        .companyJpa(companyJpa)
                        .replaceWorkerName("test1")
                        .replaceDate(LocalDate.of(2023, 5, 29))
                        .enterTime(LocalDateTime.of(2023, 5, 29, 18, 0))
                        .exitTime(LocalDateTime.of(2023, 5, 29, 20, 0))
                        .build();
        replaceWorkerJpaList.add(replaceJpaForWorkerRepository.save(replaceWorkerJpa1));
        replaceWorkerJpaList.add(replaceJpaForWorkerRepository.save(replaceWorkerJpa2));
        replaceWorkerJpaList.add(replaceJpaForWorkerRepository.save(replaceWorkerJpa3));
        return replaceWorkerJpaList;
    }

    private ContractJpa setDefaultContractJpa(
            AccountJpa savedAccountJpa, CompanyJpa savedCompanyJpa) {
        return ContractJpa.builder()
                .accountJpa(savedAccountJpa)
                .companyJpa(savedCompanyJpa)
                .workPlaceAddress("testAddress")
                .inviteCode("testCode")
                .startPeriod(LocalDate.now())
                .endPeriod(LocalDate.now())
                .workTime("09:00 - 18:00")
                .wage(10000)
                .contractInfoAgreement(false)
                .build();
    }

    private List<ContractInfo> setDefaultContractInfoList() {
        List<ContractInfo> contractInfoList = new ArrayList<>();
        ContractInfo contractInfo1 = ContractInfo.builder().contractInfoAgreement(true).build();
        ContractInfo contractInfo2 = ContractInfo.builder().contractInfoAgreement(false).build();
        contractInfoList.add(contractInfo1);
        contractInfoList.add(contractInfo2);
        return contractInfoList;
    }

    private List<WaitingListJpa> setDefaultListOfWaitingList(
            List<AccountJpa> accountJpaList, CompanyJpa companyJpa) {
        List<WaitingListJpa> waitingListJpaList = new ArrayList<>();
        WaitingListJpa waitingListJpa1 =
                WaitingListJpa.builder()
                        .accountJpa(accountJpaList.get(0))
                        .companyJpa(companyJpa)
                        .waitingStatusJpa(WaitingStatusJpa.INVITED)
                        .build();
        WaitingListJpa savedWaitingJpa1 = waitingListJpaForWorkerRepository.save(waitingListJpa1);
        WaitingListJpa waitingListJpa2 =
                WaitingListJpa.builder()
                        .accountJpa(accountJpaList.get(1))
                        .companyJpa(companyJpa)
                        .waitingStatusJpa(WaitingStatusJpa.INVITED)
                        .build();
        WaitingListJpa savedWaitingJpa2 = waitingListJpaForWorkerRepository.save(waitingListJpa2);
        waitingListJpaList.add(savedWaitingJpa1);
        waitingListJpaList.add(savedWaitingJpa2);
        return waitingListJpaList;
    }

    private List<AccountJpa> setDefaultEmployeeAccountJpaList() {
        List<AccountJpa> accountJpaList = new ArrayList<>();
        AccountJpa accountJpa1 =
                AccountJpa.builder()
                        .email("testEmployee@email.com")
                        .password("testPwd")
                        .userName("testName1")
                        .phoneNumber("010-1111-1111")
                        .gender(GenderTypeJpa.MALE.getGender())
                        .birth(LocalDate.of(2023, 1, 1))
                        .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                        .roles("EMPLOYEE")
                        .build();
        AccountJpa accountJpa2 =
                AccountJpa.builder()
                        .email("testEmployee2@email.com")
                        .password("testPwd2")
                        .userName("testName2")
                        .phoneNumber("010-2222-2222")
                        .gender(GenderTypeJpa.MALE.getGender())
                        .birth(LocalDate.of(2023, 2, 1))
                        .baseStatusJpa(BaseEntity.BaseStatusJpa.ACTIVE)
                        .roles("EMPLOYEE")
                        .build();
        AccountJpa savedAccountJpa1 = accountJpaForWorkerRepository.save(accountJpa1);
        AccountJpa savedAccountJpa2 = accountJpaForWorkerRepository.save(accountJpa2);
        accountJpaList.add(savedAccountJpa1);
        accountJpaList.add(savedAccountJpa2);
        return accountJpaList;
    }
}
