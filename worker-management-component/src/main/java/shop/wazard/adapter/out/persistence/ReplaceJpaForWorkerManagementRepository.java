package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.worker.ReplaceWorkerJpa;

import java.util.List;

interface ReplaceJpaForWorkerManagementRepository extends JpaRepository<ReplaceWorkerJpa, Long> {

    @Query("select rw from ReplaceWorkerJpa rw where rw.companyJpa.id = :companyId and rw.baseStatusJpa = 'ACTIVE' order by rw.replaceDate asc")
    List<ReplaceWorkerJpa> findAllReplaceRecord(@Param("companyId") Long companyId);

}
