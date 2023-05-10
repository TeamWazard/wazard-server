package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.port.domain.LogoImage;
import shop.wazard.entity.company.LogoImageJpa;

@Component
class FileMapper {

    public LogoImageJpa toLogoImageJpa(LogoImage logoImage) {
        return LogoImageJpa.builder()
                .logoImageUrl(logoImage.getLogoImageUrl())
                .build();
    }

    public LogoImage toLogoImage(LogoImageJpa logoImageJpa) {
        return LogoImage.builder()
                .id(logoImageJpa.getId())
                .logoImageUrl(logoImageJpa.getLogoImageUrl())
                .build();
    }

}
