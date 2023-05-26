package shop.wazard.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecordEnterTimeReqDto {

    private Long accountId;

    private Long companyId;

    private boolean tardy;

}
