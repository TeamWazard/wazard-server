package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.commuteRecord.CommuteRecordJpa;

public interface CommuteRecordJpaForAttendanceRepository extends JpaRepository<CommuteRecordJpa, Long> {
}
