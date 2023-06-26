package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.wazard.entity.contract.ContractJpa;

public interface ContractJpaForWorkerRepository extends JpaRepository<ContractJpa, Long> {

    @Query(
            "select c from ContractJpa c where c.accountJpa.id = :accountId and c.inviteCode = :invitationCode and c.baseStatusJpa = 'ACTIVE'")
    ContractJpa findContractInfo(
            @Param("invitationCode") String invitationCode, @Param("accountId") Long accountId);
}
