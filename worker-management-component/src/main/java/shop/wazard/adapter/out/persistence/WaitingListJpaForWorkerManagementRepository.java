package shop.wazard.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.WaitingListJpa;

interface WaitingListJpaForWorkerManagementRepository extends JpaRepository<WaitingListJpa, Long> {

    @Query(
            "select w from WaitingListJpa w where w.companyJpa.id = :companyId and w.waitingStatusJpa <> 'JOINED' and w.baseStatusJpa = 'ACTIVE'")
    List<WaitingListJpa> findWaitingWorkers(@Param("companyId") Long companyId);
}
