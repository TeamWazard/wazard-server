package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.company.RosterJpa;

import java.util.List;

interface RosterJpaForMyPageRepository extends JpaRepository<RosterJpa, Long> {

    @Query("select r.companyJpa from RosterJpa r where r.accountJpa.id = :accountId and r.baseStatusJpa = 'INACTIVE'")
    List<CompanyJpa> findPastWorkplacesById(@Param("accountId") Long accountId);

}
