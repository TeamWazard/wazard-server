package shop.wazard.application.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import shop.wazard.dto.RegisterReplaceReqDto;

@Getter
@Builder
public class ReplaceInfo {

    private Long companyId;
    private String replaceWorkerName;
    private LocalDate replaceDate;
    private LocalDateTime enterTime;
    private LocalDateTime exitTime;

    public static ReplaceInfo createReplace(RegisterReplaceReqDto registerReplaceReqDto) {
        return ReplaceInfo.builder()
                .companyId(registerReplaceReqDto.getCompanyId())
                .replaceWorkerName(registerReplaceReqDto.getReplaceWorkerName())
                .replaceDate(registerReplaceReqDto.getReplaceDate())
                .enterTime(registerReplaceReqDto.getEnterTime())
                .exitTime(registerReplaceReqDto.getExitTime())
                .build();
    }
}
