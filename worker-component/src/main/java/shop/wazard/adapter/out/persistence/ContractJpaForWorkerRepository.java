package shop.wazard.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.contract.ContractJpa;

interface ContractJpaForWorkerRepository extends JpaRepository<ContractJpa, Long> {

    @Query(
            "select c from ContractJpa c where c.accountJpa.id = :accountId and c.inviteCode = :invitationCode and c.baseStatusJpa = 'ACTIVE'")
    Optional<ContractJpa> findByIdAndInviteCode(
            @Param("accountId") Long accountId, @Param("invitationCode") String invitationCode);
}
