package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.contract.ContractJpa;

interface ContractForWorkerManagementRepository extends JpaRepository<ContractJpa, Long> {}
