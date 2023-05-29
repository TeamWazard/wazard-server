package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.worker.ReplaceWorkerJpa;

import java.util.List;

interface ReplaceJpaForWorkerRepository extends JpaRepository<ReplaceWorkerJpa, Long> {

    @Query("select rw from ReplaceWorkerJpa rw where rw.accountJpa = :accountJpa and rw.companyJpa.id = :companyId order by rw.replaceDate asc")
    List<ReplaceWorkerJpa> findMyReplace(@Param("companyId") Long companyId, @Param("accountJpa") AccountJpa accountJpa);

}
