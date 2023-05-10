package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.account.AccountJpa;

interface AccountJpaForCompanyManagementRepository extends JpaRepository<AccountJpa, Long> {

    AccountJpa findByEmail(String email);

}
