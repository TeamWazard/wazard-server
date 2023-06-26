package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.contract.ContractJpa;

interface ContractJpaForWorkerRepository extends JpaRepository<ContractJpa, Long> {}
