package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;

import java.util.Optional;

interface EnterRecordJpaForAttendanceRepository extends JpaRepository<EnterRecordJpa, Long> {

    @Query("select er from EnterRecordJpa er where er.accountJpa.id = :accountId and er.companyJpa.id = :companyId and er.baseStatusJpa = 'ACTIVE'")
    Optional<EnterRecordJpa> findEnterRecordJpa(@Param("accountId") Long accountId, @Param("companyId") Long companyId);

}
