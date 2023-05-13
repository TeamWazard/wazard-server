package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.company.CompanyJpa;

public interface CompanyJpaForAttendanceRepository extends JpaRepository<CompanyJpa, Long> {
}
