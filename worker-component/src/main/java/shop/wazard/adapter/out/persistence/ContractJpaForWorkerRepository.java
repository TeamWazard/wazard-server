package shop.wazard.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.contract.ContractJpa;

interface ContractJpaForWorkerRepository extends JpaRepository<ContractJpa, Long> {

    Optional<ContractJpa> findByInviteCode(@Param("invitationCode") String invitationCode);
}
