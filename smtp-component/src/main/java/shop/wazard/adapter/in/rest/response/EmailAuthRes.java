package shop.wazard.adapter.in.rest.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailAuthRes {

    private String authenticationCode;

}