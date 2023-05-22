package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.company.CompanyJpa;

import java.util.List;

interface CompanyJpaForMyPageRepository extends JpaRepository<CompanyJpa, Long> {

    @Query("select c from CompanyJpa c inner join c.rosterJpaList r where r.accountJpa.id = :accountId and r.baseStatusJpa = 'INACTIVE'")
    List<CompanyJpa> findPastWorkplacesById(@Param("accountId") Long accountId);

}
