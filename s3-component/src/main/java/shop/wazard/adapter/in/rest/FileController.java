package shop.wazard.adapter.in.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.wazard.application.port.in.FileService;
import shop.wazard.dto.UploadLogoImageResDto;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/upload")
class FileController {

    private final FileService fileService;

    @PostMapping("/store")
    public ResponseEntity<UploadLogoImageResDto> uploadLogoImage(MultipartFile multipartFile) throws IOException {
        UploadLogoImageResDto uploadLogoImageResDto = fileService.uploadLogoImage(multipartFile);
        return ResponseEntity.ok(uploadLogoImageResDto);
    }

}
