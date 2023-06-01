package shop.wazard.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetMyReplaceResDto {

    private String userName;

    private String replaceWorkerName;

    private LocalDate replaceDate;

    private LocalDateTime enterTime;

    private LocalDateTime exitTime;

}
