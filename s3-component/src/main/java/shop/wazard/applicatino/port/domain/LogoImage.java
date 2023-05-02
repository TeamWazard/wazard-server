package shop.wazard.applicatino.port.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogoImage {

    private Long id;
    private String imageName;
    private String imageUrl;

}
