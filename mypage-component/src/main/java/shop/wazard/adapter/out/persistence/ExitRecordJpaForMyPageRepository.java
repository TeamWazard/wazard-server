package shop.wazard.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.wazard.entity.common.BaseEntity.BaseStatusJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.commuteRecord.ExitRecordJpa;

public interface ExitRecordJpaForMyPageRepository extends JpaRepository<ExitRecordJpa, Long> {

    ExitRecordJpa findTopByEnterRecordJpaAndBaseStatusJpaOrderByIdDesc(EnterRecordJpa enterRecordJpa, BaseStatusJpa baseStatusJpa);

}
