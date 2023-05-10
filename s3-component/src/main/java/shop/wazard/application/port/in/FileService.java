package shop.wazard.application.port.in;

import org.springframework.web.multipart.MultipartFile;
import shop.wazard.dto.UploadLogoImageResDto;

import java.io.IOException;

public interface FileService {

    UploadLogoImageResDto uploadLogoImage(MultipartFile multipartFile) throws IOException;

}
