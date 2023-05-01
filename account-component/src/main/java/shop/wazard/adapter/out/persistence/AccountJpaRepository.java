package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.wazard.entity.account.AccountJpa;

import java.util.Optional;

@Repository
interface AccountJpaRepository extends JpaRepository<AccountJpa, Long> {

    Long findIdByEmail(String email);

    AccountJpa save(AccountJpa accountJpa);

    Optional<AccountJpa> findByEmail(String email);

}