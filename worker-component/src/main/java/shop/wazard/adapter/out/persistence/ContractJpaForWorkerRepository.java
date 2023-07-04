package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.contract.ContractJpa;

import java.util.Optional;

interface ContractJpaForWorkerRepository extends JpaRepository<ContractJpa, Long> {

    Optional<ContractJpa> findByInviteCode(
            @Param("invitationCode") String invitationCode);
}
