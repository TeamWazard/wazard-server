package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.worker.WorkerJpa;

interface WorkerRepository extends JpaRepository<WorkerJpa, Long> {
}
