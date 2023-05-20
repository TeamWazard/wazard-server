package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.company.CompanyJpa;

import java.time.LocalDate;
import java.util.List;

public interface EnterRecordJpaForAttendanceRepository extends JpaRepository<EnterRecordJpa, Long> {

    @Query("select er from EnterRecordJpa er where er.companyJpa = :companyJpa and er.enterDate = :date order by er.companyJpa.id asc")
    List<EnterRecordJpa> findAllByCompanyJpaAndEnterDateOrderByAccountJpaAsc(@Param("companyJpa") CompanyJpa companyJpa, @Param("date") LocalDate date);

}
