package shop.wazard.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMyPastWorkRecordReqDto {

    private String email;
    private Long companyId;

}
