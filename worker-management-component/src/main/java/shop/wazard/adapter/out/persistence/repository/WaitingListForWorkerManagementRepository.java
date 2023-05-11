package shop.wazard.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.company.WaitingListJpa;

public interface WaitingListForWorkerManagementRepository extends JpaRepository<WaitingListJpa, Long> {
}
