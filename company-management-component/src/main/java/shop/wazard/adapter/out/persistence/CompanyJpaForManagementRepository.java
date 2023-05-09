package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.CompanyJpa;

interface CompanyJpaForManagementRepository extends JpaRepository<CompanyJpa, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update CompanyJpa c set c.stateJpa = 'INACTIVE' where c.id = :companyId")
    void deleteCompany(@Param("companyId") Long companyId);

}
