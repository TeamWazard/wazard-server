package shop.wazard.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.company.RosterJpa;

public interface RosterForWorkerManagementRepository extends JpaRepository<RosterJpa, Long> {
}
