package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.common.BaseEntity.BaseStatusJpa;

import java.util.Optional;

interface AccountJpaForMyPageRepository extends JpaRepository<AccountJpa, Long> {

    Optional<AccountJpa> findByEmailAndBaseStatusJpa(String email, BaseStatusJpa baseStatusJpa);

}
