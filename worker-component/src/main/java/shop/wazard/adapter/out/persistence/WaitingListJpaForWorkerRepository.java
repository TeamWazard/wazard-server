package shop.wazard.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.WaitingListJpa;

interface WaitingListJpaForWorkerRepository extends JpaRepository<WaitingListJpa, Long> {
    @Query(
            "select wl from WaitingListJpa wl where wl.accountJpa.id = :accountId and wl.companyJpa.id = :companyId and wl.baseStatusJpa = 'ACTIVE'")
    Optional<WaitingListJpa> findWaitingListByAccountIdAndCompanyId(
            @Param("accountId") Long accountId, @Param("companyId") Long companyId);
}
