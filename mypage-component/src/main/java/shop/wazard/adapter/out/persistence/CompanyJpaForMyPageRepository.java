package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyJpa;

import java.util.Optional;

interface CompanyJpaForMyPageRepository extends JpaRepository<CompanyJpa, Long> {

    @Query("select c from CompanyJpa c join c.rosterJpaList r where r.accountJpa.id = :accountId and r.companyJpa.id = :companyId and r.baseStatusJpa = 'ACTIVE'")
    CompanyJpa findCompanyJpaByAccountIdAndCompanyId(@Param("accountId") Long accountId, @Param("companyId") Long companyId);

    Optional<CompanyJpa> findByIdAndBaseStatusJpa(Long companyId, BaseEntity.BaseStatusJpa status);

}
