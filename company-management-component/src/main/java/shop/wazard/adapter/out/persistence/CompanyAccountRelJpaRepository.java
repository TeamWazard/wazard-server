package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.company.CompanyAccountRelJpa;

public interface CompanyAccountRelJpaRepository extends JpaRepository<CompanyAccountRelJpa, Long> {
}
