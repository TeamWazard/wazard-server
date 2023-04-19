package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface AccountJpaRepository extends JpaRepository<Account, Long> {

    @Query(value = "select a.id from Account a where a.email = :email")
    Long findIdByEmail(@Param("email") String email);

    Account save(Account account);

    Account findByEmail(String email);

}