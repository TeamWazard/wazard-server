package shop.wazard.entity.company;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.wazard.entity.common.BaseEntity;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "LogoImage")
public class LogoImageJpa extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "imageId")
    private Long id;

    private String imageUrl;

    @Builder
    public LogoImageJpa(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
