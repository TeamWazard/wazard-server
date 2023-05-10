package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.RosterJpa;

interface RosterJpaForCompanyManagementRepository extends JpaRepository<RosterJpa, Long> {

    RosterJpa save(RosterJpa rosterJpa);

    @Modifying(clearAutomatically = true)
    @Query("update RosterJpa r set r.stateJpa = 'INACTIVE' where r.companyJpa.id = :companyId")
    void deleteCompanyAccountRel(@Param("companyId") Long companyId);

}
