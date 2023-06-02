package shop.wazard.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.worker.ReplaceWorkerJpa;

interface ReplaceJpaForWorkerRepository extends JpaRepository<ReplaceWorkerJpa, Long> {

    @Query(
            "select rw from ReplaceWorkerJpa rw where rw.accountJpa.id = :accountId and rw.companyJpa.id = :companyId and rw.baseStatusJpa = 'ACTIVE' order by rw.replaceDate asc")
    List<ReplaceWorkerJpa> findMyReplaceRecord(
            @Param("companyId") Long companyId, @Param("accountId") Long accountId);
}
