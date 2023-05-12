package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.account.AccountJpa;

import java.util.Optional;

interface AccountJpaForAttendanceRepository extends JpaRepository<AccountJpa, Long> {

    Optional<AccountJpa> findByEmail(String email);

}
