package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.wazard.entity.company.LogoImageJpa;

@Repository
interface FileJpaRepository extends JpaRepository<LogoImageJpa, Long> {

    public LogoImageJpa save(LogoImageJpa logoImageJpa);
}
