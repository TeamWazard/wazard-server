package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.commuteRecord.ExitRecordJpa;

interface ExitRecordJpaForAttendanceRepository extends JpaRepository<ExitRecordJpa, Long> {

    @Query("select ex from ExitRecordJpa ex where ex.enterRecordJpa.id = :enterRecordId")
    ExitRecordJpa findAllByEnterRecordJpa(@Param("enterRecordId") Long enterRecordId);
}
