package shop.wazard.application.port.out;

import shop.wazard.application.port.domain.LogoImage;

public interface SaveFileRepository {

    LogoImage uploadStoreLogo(LogoImage logoImage);
}
