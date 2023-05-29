package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.commuteRecord.ExitRecordJpa;

interface ExitRecordJpaForWorkerManagementRepository extends JpaRepository<ExitRecordJpa, Long> {



}
