package shop.wazard.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.wazard.application.port.domain.LogoImage;
import shop.wazard.application.port.in.FileService;
import shop.wazard.application.port.out.SaveFileRepository;
import shop.wazard.dto.UploadLogoImageResDto;
import shop.wazard.util.exception.StatusEnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@Transactional
class FileServiceImpl implements FileService {

    private AmazonS3 amazonS3;
    private SaveFileRepository saveFileRepository;
    private String bucket;

    public FileServiceImpl(AmazonS3 amazonS3, SaveFileRepository saveFileRepository, @Value("${cloud.aws.s3.bucket}") String bucket) {
        this.amazonS3 = amazonS3;
        this.saveFileRepository = saveFileRepository;
        this.bucket = bucket;
    }


    @Override
    public UploadLogoImageResDto uploadLogoImage(MultipartFile multipartFile) throws IOException {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);
        String storeFileName = UUID.randomUUID() + "." + ext;
        String key = "store/logo/" + storeFileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new IOException(StatusEnum.FAIL_TO_UPLOAD_IMAGE.getMessage());
        }
        String storeFileUrl = amazonS3.getUrl(bucket, key).toString();
        LogoImage logoImage = LogoImage.builder()
                .logoImageUrl(storeFileUrl)
                .build();
        LogoImage uploadedLogoImage = saveFileRepository.uploadStoreLogo(logoImage);
        return UploadLogoImageResDto.builder()
                .message("업로드 되었습니다.")
                .imageId(uploadedLogoImage.getId())
                .imageUrl(uploadedLogoImage.getLogoImageUrl())
                .build();
    }

}
