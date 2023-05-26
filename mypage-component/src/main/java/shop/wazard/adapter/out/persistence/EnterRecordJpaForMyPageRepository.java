package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;

public interface EnterRecordJpaForMyPageRepository extends JpaRepository<EnterRecordJpa, Long> {

    @Query("select count(e.id) from EnterRecordJpa e where e.accountJpa.id = :accountId and e.companyJpa.id = :companyId and e.tardy = true and e.baseStatusJpa = 'ACTIVE'")
    int countTardyByAccountIdAndCompanyId(@Param("accountId") Long accountId, @Param("companyId") Long companyId);

}
