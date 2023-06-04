package shop.wazard.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.account.AccountJpa;

interface AccountJpaForAttendanceRepository extends JpaRepository<AccountJpa, Long> {

    Optional<AccountJpa> findByEmail(String email);
}
