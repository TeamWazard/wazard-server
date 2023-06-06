package shop.wazard.application.port.in;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import shop.wazard.dto.UploadLogoImageResDto;

public interface FileService {

    UploadLogoImageResDto uploadLogoImage(MultipartFile multipartFile) throws IOException;
}
