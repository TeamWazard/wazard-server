package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.commuteRecord.CommuteRecordJpa;

public interface CommuteRecordJpaForWorkerManagementRepository extends JpaRepository<CommuteRecordJpa, Long> {
}