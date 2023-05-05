package shop.wazard.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public class BaseEntity {
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected State state;

    @Getter
    @AllArgsConstructor
    public enum State {
        ACTIVE("ACTIVE"),
        INACTIVE("INACTIVE");

        private final String status;
    }

    @PrePersist
    private void prePersistFunction() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        state = State.ACTIVE;
    }

    @PreUpdate
    private void preUpdateFunction() {
        LocalDateTime now = LocalDateTime.now();
        updatedAt = now;
    }

}
