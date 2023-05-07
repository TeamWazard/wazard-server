package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.CompanyAccountRelJpa;

interface CompanyAccountRelJpaRepository extends JpaRepository<CompanyAccountRelJpa, Long> {

    CompanyAccountRelJpa save(CompanyAccountRelJpa companyAccountRelJpa);

    @Modifying(clearAutomatically = true)
    @Query("update CompanyAccountRelJpa car set car.stateJpa = 'INACTIVE' where car.companyJpa = :companyId")
    void deleteCompany(@Param("companyId") Long companyId);

}
