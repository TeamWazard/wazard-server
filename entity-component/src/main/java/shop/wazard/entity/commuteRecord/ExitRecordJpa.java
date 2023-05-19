package shop.wazard.entity.commuteRecord;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.common.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public ExitRecordJpa(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

}
