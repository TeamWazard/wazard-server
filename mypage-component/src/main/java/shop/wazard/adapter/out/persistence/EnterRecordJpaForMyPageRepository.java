package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.common.BaseEntity.BaseStatusJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.company.CompanyJpa;

public interface EnterRecordJpaForMyPageRepository extends JpaRepository<EnterRecordJpa, Long> {

    @Query("select count(e.id) from EnterRecordJpa e where e.accountJpa.id = :accountId and e.companyJpa.id = :companyId and e.tardy = true and e.baseStatusJpa = 'ACTIVE'")
    int countTardyByAccountIdAndCompanyId(@Param("accountId") Long accountId, @Param("companyId") Long companyId);

    EnterRecordJpa findTopByAccountJpaAndCompanyJpaAndBaseStatusJpaOrderByIdAsc(AccountJpa accountJpa, CompanyJpa companyJpa, BaseStatusJpa baseStatusJpa);

    EnterRecordJpa findTopByAccountJpaAndCompanyJpaAndBaseStatusJpaOrderByIdDesc(AccountJpa savedAccountJpa, CompanyJpa savedCompanyJpa, BaseStatusJpa active);

    @Query("select count (distinct e.id) from EnterRecordJpa e")
    int countTotalWorkDayByAccountIdAndCompanyId(Long accountId, Long companyId);
}
