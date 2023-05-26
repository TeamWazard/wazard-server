package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.worker.ReplaceWorkerJpa;

interface ReplaceJpaForWorkerRepository extends JpaRepository<ReplaceWorkerJpa, Long> {
}
