package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadStoreLogoResDto {

    private String message;
    private Long imageId;
    private String imageUrl;

}
