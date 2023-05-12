package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.company.WaitingListJpa;

interface WaitingListJpaForWorkerManagementRepository extends JpaRepository<WaitingListJpa, Long> {
}
