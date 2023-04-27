package shop.wazard.util.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import shop.wazard.util.response.StatusEnum;

@Getter
@Builder
@AllArgsConstructor
public class WazardException extends RuntimeException{
    private final StatusEnum statusEnum;
}
