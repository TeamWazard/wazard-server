package shop.wazard.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.commuteRecord.AbsentJpa;

interface AbsentRecordJpaForWorkerManagementRepository extends JpaRepository<AbsentJpa, Long> {

    @Query(
            "select a from AbsentJpa a where a.accountJpa.id = :accountId and a.companyJpa.id = :companyId and :startDate <= a.absentDate and a.absentDate < :endDate and a.baseStatusJpa = 'ACTIVE'")
    List<AbsentJpa> findAllAbsentRecordOfWorker(
            @Param("accountId") Long accountId,
            @Param("companyId") Long companyId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
