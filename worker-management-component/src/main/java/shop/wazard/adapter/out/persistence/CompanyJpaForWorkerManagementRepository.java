package shop.wazard.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.CompanyJpa;

interface CompanyJpaForWorkerManagementRepository extends JpaRepository<CompanyJpa, Long> {
    @Query(
            "select c from CompanyJpa c join c.rosterJpaList r where r.accountJpa.id = :accountId and r.baseStatusJpa = 'INACTIVE'")
    List<CompanyJpa> findAllWorkerPastCompaniesByAccountId(@Param("accountId") Long accountId);
}
