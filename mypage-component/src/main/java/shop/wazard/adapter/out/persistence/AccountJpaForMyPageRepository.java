package shop.wazard.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.common.BaseEntity.BaseStatusJpa;

interface AccountJpaForMyPageRepository extends JpaRepository<AccountJpa, Long> {

    Optional<AccountJpa> findByEmailAndBaseStatusJpa(String email, BaseStatusJpa baseStatusJpa);

    Optional<AccountJpa> findByIdAndBaseStatusJpa(Long accountId, BaseStatusJpa active);
}
