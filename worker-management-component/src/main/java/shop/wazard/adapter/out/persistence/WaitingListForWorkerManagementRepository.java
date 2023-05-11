package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.WaitingListJpa;

import java.util.Optional;

interface WaitingListForWorkerManagementRepository extends JpaRepository<WaitingListJpa, Long> {
    Optional<WaitingListJpa> findByAccountJpaAndCompanyJpa(AccountJpa accountJpa, CompanyJpa companyJpa);
}
