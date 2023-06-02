package shop.wazard.adapter.out.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.CompanyJpa;

interface CompanyJpaRepository extends JpaRepository<CompanyJpa, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update CompanyJpa c set c.baseStatusJpa = 'INACTIVE' where c.id = :companyId")
    void deleteCompany(@Param("companyId") Long companyId);

    @Query(
            "select c from CompanyJpa c inner join c.rosterJpaList r where r.accountJpa.id = :accountId and r.baseStatusJpa = 'ACTIVE'")
    List<CompanyJpa> findOwnedCompanyList(@Param("accountId") Long accountId);

    @Query(
            "select c from CompanyJpa c inner join c.rosterJpaList r where r.accountJpa.id = :accountId and r.baseStatusJpa = 'ACTIVE'")
    List<CompanyJpa> findBelongedCompanyList(@Param("accountId") Long accountId);
}
