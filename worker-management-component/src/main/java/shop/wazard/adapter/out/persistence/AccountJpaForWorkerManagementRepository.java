package shop.wazard.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.account.AccountJpa;

interface AccountJpaForWorkerManagementRepository extends JpaRepository<AccountJpa, Long> {

    Optional<AccountJpa> findByEmail(String email);

    @Query(
            "select a from AccountJpa a inner join a.rosterJpaList r where r.companyJpa.id = :companyId and r.rosterTypeJpa = 'EMPLOYEE' and r.baseStatusJpa = 'ACTIVE'")
    List<AccountJpa> findWorkersBelongedToCompany(@Param("companyId") Long companyId);
}
