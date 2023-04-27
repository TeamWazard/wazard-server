package shop.wazard.util.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WazardException extends RuntimeException{
    private final StatusEnum statusEnum;
}
