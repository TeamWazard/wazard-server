package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.company.CompanyJpa;

interface CompanyJpaForManagementRepository extends JpaRepository<CompanyJpa, Long> {


}
