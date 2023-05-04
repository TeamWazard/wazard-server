package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.account.AccountJpa;

import java.util.Optional;

interface AccountJpaRepository extends JpaRepository<AccountJpa, Long> {

    Long findIdByEmail(String email);

    AccountJpa save(AccountJpa accountJpa);

    Optional<AccountJpa> findByEmail(String email);

}