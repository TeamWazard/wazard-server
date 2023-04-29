package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface AccountJpaRepository extends JpaRepository<AccountJpa, Long> {

    @Query(value = "select a.id from AccountJpa a where a.email = :email")
    Long findIdByEmail(@Param("email") String email);

    AccountJpa save(AccountJpa accountJpa);

    Optional<AccountJpa> findByEmail(String email);

}