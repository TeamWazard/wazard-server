package shop.wazard.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.account.AccountJpa;

interface AccountJpaRepository extends JpaRepository<AccountJpa, Long> {

    Long findIdByEmail(String email);

    AccountJpa save(AccountJpa accountJpa);

    Optional<AccountJpa> findByEmail(String email);
}
