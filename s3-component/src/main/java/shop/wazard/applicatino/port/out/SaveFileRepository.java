package shop.wazard.applicatino.port.out;

import shop.wazard.applicatino.port.domain.LogoImage;

public interface SaveFileRepository {

    LogoImage uploadStoreLogo(LogoImage logoImage);

}
