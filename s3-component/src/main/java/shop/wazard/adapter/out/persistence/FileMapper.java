package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.port.domain.LogoImage;
import shop.wazard.entity.company.LogoImageJpa;

@Component
class FileMapper {

    public LogoImageJpa toLogoImageJpa(LogoImage logoImage) {
        return LogoImageJpa.builder()
                .imageUrl(logoImage.getImageUrl())
                .build();
    }

    public LogoImage toLogoImage(LogoImageJpa logoImageJpa) {
        return LogoImage.builder()
                .id(logoImageJpa.getId())
                .imageUrl(logoImageJpa.getImageUrl())
                .build();
    }

}
