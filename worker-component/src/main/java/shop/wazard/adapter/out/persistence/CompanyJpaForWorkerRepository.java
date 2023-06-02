package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.company.CompanyJpa;

interface CompanyJpaForWorkerRepository extends JpaRepository<CompanyJpa, Long> {}
