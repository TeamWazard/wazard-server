package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;

public interface CommuteRecordJpaForAttendanceRepository extends JpaRepository<EnterRecordJpa, Long> {
}
