package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.wazard.entity.account.AccountJpa;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountJpa, Long> {


    AccountJpa findByEmail(String email);

}
