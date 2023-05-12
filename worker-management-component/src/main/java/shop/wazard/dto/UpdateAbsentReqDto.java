package shop.wazard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAbsentReqDto {

    private String email;
    private Long accountId;
    private Long companyId;

}
