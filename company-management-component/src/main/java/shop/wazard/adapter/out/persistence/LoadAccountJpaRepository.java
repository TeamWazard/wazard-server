package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.wazard.entity.account.AccountJpa;

@Repository
public interface LoadAccountJpaRepository extends JpaRepository<AccountJpa, Long> {
    @Query("select aj from AccountJpa aj where aj.email = :email")
    AccountJpa findAccountByEmail(@Param("email") String email);

}
