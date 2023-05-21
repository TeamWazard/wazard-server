package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.commuteRecord.ExitRecordJpa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface ExitRecordJpaForAttendanceRepository extends JpaRepository<ExitRecordJpa, Long> {

    @Query("select ex from ExitRecordJpa ex where ex.enterRecordJpa.id = :enterRecordId")
    ExitRecordJpa findAllByEnterRecordJpa(@Param("enterRecordId") Long enterRecordId);

}
