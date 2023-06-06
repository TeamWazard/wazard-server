package shop.wazard.entity.worker;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.account.AccountJpa;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Worker")
public class WorkerJpa {

    @Id
    @GeneratedValue
    @Column(name = "workerId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false)
    private AccountJpa accountJpa;

    private String replaceWorkerName;

    private LocalDateTime replaceDate;

    private LocalDateTime enterTime;

    private LocalDateTime exitTime;
}
