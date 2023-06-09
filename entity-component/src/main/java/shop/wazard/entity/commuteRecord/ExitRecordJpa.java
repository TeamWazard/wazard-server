package shop.wazard.entity.commuteRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.common.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "ExitRecord")
public class ExitRecordJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "exitRecordId")
    private Long id;

    @OneToOne
    @JoinColumn(name = "enterRecordId")
    private EnterRecordJpa enterRecordJpa;

    private LocalDate exitDate;

    private LocalDateTime exitTime;

    @Builder
    public ExitRecordJpa(
            EnterRecordJpa enterRecordJpa, LocalDate exitDate, LocalDateTime exitTime) {
        this.enterRecordJpa = enterRecordJpa;
        this.exitDate = exitDate;
        this.exitTime = exitTime;
    }
}
