package shop.wazard.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.account.AccountJpa;

public interface AccountForWorkerManagementRepository extends JpaRepository<AccountJpa, Long> {
}
