package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.company.CompanyAccountRelJpa;

interface RelationRepository extends JpaRepository<CompanyAccountRelJpa, Long> {


}
