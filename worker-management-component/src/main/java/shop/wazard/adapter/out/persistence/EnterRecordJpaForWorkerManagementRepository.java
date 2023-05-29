package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;

interface EnterRecordJpaForWorkerManagementRepository extends JpaRepository<EnterRecordJpa, Long> {



}
