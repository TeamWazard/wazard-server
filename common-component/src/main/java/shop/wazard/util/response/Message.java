package shop.wazard.util.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class Message {

    private HttpStatus httpStatus;
    private Object result;

}
