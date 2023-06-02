package shop.wazard.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;

interface EnterRecordJpaForWorkerManagementRepository extends JpaRepository<EnterRecordJpa, Long> {

    @Query(
            "select e from EnterRecordJpa e where e.accountJpa.id = :accountId and e.companyJpa.id = :companyId and :startDate <= e.enterDate and e.enterDate < :endDate and e.baseStatusJpa = 'ACTIVE'")
    List<EnterRecordJpa> findAllRecordOfWorker(
            @Param("accountId") Long accountId,
            @Param("companyId") Long companyId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
