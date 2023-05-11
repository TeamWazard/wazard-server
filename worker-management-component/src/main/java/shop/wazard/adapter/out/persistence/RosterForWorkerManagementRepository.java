package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.company.RosterJpa;

interface RosterForWorkerManagementRepository extends JpaRepository<RosterJpa, Long> {
}
