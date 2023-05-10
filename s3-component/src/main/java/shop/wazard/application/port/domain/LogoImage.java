package shop.wazard.application.port.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogoImage {

    private Long id;
    private String logoImageName;
    private String logoImageUrl;

}
