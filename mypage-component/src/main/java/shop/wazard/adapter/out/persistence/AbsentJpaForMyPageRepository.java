package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.commuteRecord.AbsentJpa;

public interface AbsentJpaForMyPageRepository extends JpaRepository<AbsentJpa, Long> {

    @Query("select count(ab.id) from AbsentJpa ab where ab.accountJpa.id = :accountId and ab.companyJpa.id = :companyId and ab.baseStatusJpa = 'ACTIVE'")
    int countAbsentByAccountIdAndCompanyId(@Param("accountId") Long accountId, @Param("companyId") Long companyId);

}
