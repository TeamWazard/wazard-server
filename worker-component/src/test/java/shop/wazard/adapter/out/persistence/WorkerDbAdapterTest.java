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
import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.account.GenderTypeJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.worker.ReplaceWorkerJpa;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = {"shop.wazard.entity.*"})
@ContextConfiguration(classes = {EntityManager.class, WorkerDbAdapter.class, WorkerMapper.class, AccountForWorkerMapper.class, AccountJpaForWorkerRepository.class, CompanyJpaForWorkerRepository.class, ReplaceJpaForWorkerRepository.class})
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
    @DisplayName("근무자 - 대타 등록 - ReplaceWorkerJpa 저장")
    public void registerReplaceSuccess() throws Exception {
        // given

        AccountJpa accountJpa = setDefaultEmployeeAccountJpa();
        CompanyJpa companyJpa = setDefaultCompanyJpa();

        // when
        AccountJpa savedAccountJpa = accountJpaForWorkerRepository.save(accountJpa);
        CompanyJpa savedCompanyJpa = companyJpaForWorkerRepository.save(companyJpa);
        ReplaceInfo replaceInfo = ReplaceInfo.builder()
                .companyId(savedCompanyJpa.getId())
                .replaceWorkerName("test1")
                .replaceDate(LocalDate.now())
                .enterTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now())
                .build();
        ReplaceWorkerJpa result = replaceJpaForWorkerRepository.save(workerMapper.saveReplaceInfo(savedAccountJpa, savedCompanyJpa, replaceInfo));
        em.flush();

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(savedAccountJpa.getId(), result.getAccountJpa().getId()),
                () -> Assertions.assertEquals(savedCompanyJpa.getId(), result.getCompanyJpa().getId()),
                () -> Assertions.assertEquals(replaceInfo.getReplaceWorkerName(), result.getReplaceWorkerName()),
                () -> Assertions.assertEquals(replaceInfo.getReplaceDate(), result.getReplaceDate()),
                () -> Assertions.assertEquals(replaceInfo.getEnterTime(), result.getEnterTime()),
                () -> Assertions.assertEquals(replaceInfo.getExitTime(), result.getExitTime())
        );
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