package shop.wazard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermitWorkerToJoinReqDto {

    private Long waitingListId;
    private String email;

}
