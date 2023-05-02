package shop.wazard.entity.store;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "LogoImage")
public class LogoImageJpa {

    @Id
    @GeneratedValue
    @Column(name = "imageId")
    private Long id;

    private String imageName;

    private String imageUrl;

    @Builder
    public LogoImageJpa(String imageName, String imageUrl) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

}
