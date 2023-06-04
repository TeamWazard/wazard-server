package shop.wazard.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailAuthResDto {

    private String authenticationCode;
}
