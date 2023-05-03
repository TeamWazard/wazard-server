package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.applicatino.port.domain.LogoImage;
import shop.wazard.entity.company.LogoImageJpa;

@Component
class FileMapper {

    public LogoImageJpa toLogoImageJpa(LogoImage logoImage) {
        return LogoImageJpa.builder()
                .imageName(logoImage.getImageName())
                .imageUrl(logoImage.getImageUrl())
                .build();
    }

    public LogoImage toLogoImage(LogoImageJpa logoImageJpa) {
        return LogoImage.builder()
                .id(logoImageJpa.getId())
                .imageName(logoImageJpa.getImageName())
                .imageUrl(logoImageJpa.getImageUrl())
                .build();
    }

}
