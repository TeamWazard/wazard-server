package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.RosterJpa;

import java.util.Optional;

interface RosterJpaForWorkerManagementRepository extends JpaRepository<RosterJpa, Long> {

    @Query("select rj from RosterJpa rj where rj.accountJpa.id = :accountId and rj.companyJpa.id = :companyId and rj.baseStatusJpa = 'ACTIVE'")
    Optional<RosterJpa> findRosterJpaByAccountIdAndCompanyId(@Param("accountId") Long accountId, @Param("companyId") Long companyId);

}
