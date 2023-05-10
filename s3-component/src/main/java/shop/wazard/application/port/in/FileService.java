package shop.wazard.application.port.in;

import org.springframework.web.multipart.MultipartFile;
import shop.wazard.dto.UploadStoreLogoResDto;

import java.io.IOException;

public interface FileService {

    UploadStoreLogoResDto uploadLogoImage(MultipartFile multipartFile) throws IOException;

}
