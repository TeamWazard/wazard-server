package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.company.WaitingListJpa;

interface WaitingListForWorkerManagementRepository extends JpaRepository<WaitingListJpa, Long> {
}
