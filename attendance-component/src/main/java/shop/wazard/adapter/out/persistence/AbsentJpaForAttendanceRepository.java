package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.commuteRecord.AbsentJpa;

public interface AbsentJpaForAttendanceRepository extends JpaRepository<AbsentJpa, Long> {
}
