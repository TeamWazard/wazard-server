package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.RosterJpa;

import java.util.List;

interface RosterForWorkerManagementRepository extends JpaRepository<RosterJpa, Long> {

    @Query("select a from RosterJpa r inner join AccountJpa a where r.companyJpa.id = :companyId and r.baseStatusJpa = 'ACTIVE'")
    List<AccountJpa> findAllByCompanyId(@Param("companyId") Long companyId);

}
