package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.CompanyJpa;

import java.util.Optional;

import static shop.wazard.entity.common.BaseEntity.BaseStatusJpa;

interface CompanyJpaForMyPageRepository extends JpaRepository<CompanyJpa, Long> {

    @Query("select c from CompanyJpa c join c.rosterJpaList r where r.accountJpa.id = :accountId and r.companyJpa.id = :companyId and r.baseStatusJpa = 'INACTIVE'")
    Optional<CompanyJpa> findPastCompanyJpaByAccountIdAndCompanyId(@Param("accountId") Long accountId, @Param("companyId") Long companyId);

    Optional<CompanyJpa> findByIdAndBaseStatusJpa(Long companyId, BaseStatusJpa status);

}
