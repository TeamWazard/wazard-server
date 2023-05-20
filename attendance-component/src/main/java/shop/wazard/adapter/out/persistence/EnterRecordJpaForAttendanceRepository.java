package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.company.CompanyJpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface EnterRecordJpaForAttendanceRepository extends JpaRepository<EnterRecordJpa, Long> {

    @Query("select er from EnterRecordJpa er where er.accountJpa.id = :accountId and er.companyJpa.id = :companyId and er.baseStatusJpa = 'ACTIVE'")
    Optional<EnterRecordJpa> findEnterRecordJpa(@Param("accountId") Long accountId, @Param("companyId") Long companyId);

    @Query("select er from EnterRecordJpa er where er.companyJpa = :companyJpa and er.enterDate = :date and er.baseStatusJpa = 'ACTIVE' order by er.companyJpa.id asc")
    List<EnterRecordJpa> findAllByCompanyJpaAndEnterDateOrderByAccountJpaAsc(@Param("companyJpa") CompanyJpa companyJpa, @Param("date") LocalDate date);

}
