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
import shop.wazard.application.domain.AccountForWorker;
import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.worker.ReplaceWorkerJpa;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.util.exception.StatusEnum;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
@ContextConfiguration(classes = {EntityManager.class, WorkerDbAdapter.class, WorkerMapper.class, AccountForWorkerMapper.class, AccountForWorker.class, AccountJpaForWorkerRepository.class, CompanyJpaForWorkerRepository.class, ReplaceJpaForWorkerRepository.class})
class WorkerDbAdapterTest {

    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private AccountForWorkerMapper accountForWorkerMapper;
    @Autowired
    private AccountJpaForWorkerRepository accountJpaForWorkerRepository;
    @Autowired
    private CompanyJpaForWorkerRepository companyJpaForWorkerRepository;
    @Autowired
    private ReplaceJpaForWorkerRepository replaceJpaForWorkerRepository;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("근무자 - 대타 등록 - 성공")
    public void registerReplaceSuccess() throws Exception {
        // given
        AccountForWorker accountForWorker = AccountForWorker.builder()
                .id(1L)
                .roles("EMPLOYEE")
                .email("test@email.com")
                .userName("test")
                .build();
        ReplaceInfo replaceInfo = ReplaceInfo.builder()
                .companyId(1L)
                .replaceWorkerName("test1")
                .replaceDate(LocalDate.now())
                .enterTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now())
                .build();

        // when
        AccountJpa accountJpa = accountJpaForWorkerRepository.findByEmail(accountForWorker.getEmail());
        CompanyJpa companyJpa = companyJpaForWorkerRepository.findById(replaceInfo.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        ReplaceWorkerJpa result = replaceJpaForWorkerRepository.save(workerMapper.saveReplaceInfo(accountJpa, companyJpa, replaceInfo));
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(accountJpa.getId(), result.getAccountJpa().getId()),
                () -> Assertions.assertEquals(companyJpa.getId(), result.getCompanyJpa().getId()),
                () -> Assertions.assertEquals(replaceInfo.getReplaceWorkerName(), result.getReplaceWorkerName()),
                () -> Assertions.assertEquals(replaceInfo.getReplaceDate(), result.getReplaceDate()),
                () -> Assertions.assertEquals(replaceInfo.getEnterTime(), result.getEnterTime()),
                () -> Assertions.assertEquals(replaceInfo.getExitTime(), result.getExitTime())
        );
    }

}