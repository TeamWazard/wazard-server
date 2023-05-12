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
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.application.domain.WaitingStatus;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.*;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {EntityManager.class, WorkerManagementDbAdapter.class, AccountForWorkerManagementMapper.class, WorkerManagementMapper.class, RosterForWorkerManagementRepository.class, WaitingListForWorkerManagementRepository.class, AccountForWorkerManagementRepository.class})
class WorkerManagementDbAdapterTest {

    @Autowired
    private WorkerManagementMapper workerManagementMapper;
    @Autowired
    private AccountForWorkerManagementMapper accountForWorkerManagementMapper;
    @Autowired
    private RosterForWorkerManagementRepository rosterForWorkerManagementRepository;
    @Autowired
    private WaitingListForWorkerManagementRepository waitingListForWorkerManagementRepository;
    @Autowired
    private AccountForWorkerManagementRepository accountForWorkerManagementRepository;
    @Autowired
    private CompanyForWorkerManagementRepository companyForWorkerManagementRepository;
    @Autowired
    private EntityManager em;
    
    @Test
    @DisplayName("고용주 - 대기자 명단에서 특정 근무자 조회 - 성공")
    public void findWaitingInfo() throws Exception {
        // given
        WaitingListJpa waitingListJpa = setDefaultWaitingListJpa();

        // when
        WaitingListJpa savedWaitingListJpa = waitingListForWorkerManagementRepository.save(waitingListJpa);
        WaitingListJpa result = waitingListForWorkerManagementRepository.findById(savedWaitingListJpa.getId()).get();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(result.getCompanyJpa(), savedWaitingListJpa.getCompanyJpa()),
                () -> Assertions.assertEquals(result.getAccountJpa(), savedWaitingListJpa.getAccountJpa()),
                () -> Assertions.assertEquals(result.getWaitingStatusJpa(), savedWaitingListJpa.getWaitingStatusJpa())
        );
    }

    @Test
    @DisplayName("고용주 - 근무자 업장 초대 수락 상태 변경 - 성공")
    public void changeWaitingStatus() throws Exception {
        // given
        WaitingListJpa waitingListJpa = setDefaultWaitingListJpa();
        WaitingInfo waitingInfo = WaitingInfo.builder()
                .waitingStatus(WaitingStatus.JOINED)
                .build();

        // when
        WaitingListJpa savedWaitingListJpa = waitingListForWorkerManagementRepository.save(waitingListJpa);
        savedWaitingListJpa.updateWaitingStatus(WaitingStatusJpa.valueOf(waitingInfo.getWaitingStatus().getStatus()));

        // then
        Assertions.assertEquals(savedWaitingListJpa.getWaitingStatusJpa(), WaitingStatusJpa.JOINED);
    }
    
    @Test
    @DisplayName("고용주 - 업장 근무자 리스트에 근무자 추가 - 성공")
    public void joinWorker() throws Exception {
        // given
        AccountJpa accountJpa = setDefaultAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountForWorkerManagementRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyForWorkerManagementRepository.save(companyJpa);
        RosterJpa savedRosterJpa = rosterForWorkerManagementRepository.save(
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

    private WaitingListJpa setDefaultWaitingListJpa() {
        CompanyJpa companyJpa = setDefaultCompanyJpa();
        AccountJpa accountJpa = setDefaultAccountJpa();
        return WaitingListJpa.builder()
                .companyJpa(companyJpa)
                .accountJpa(accountJpa)
                .waitingStatusJpa(WaitingStatusJpa.AGREED)
                .build();
    }

}