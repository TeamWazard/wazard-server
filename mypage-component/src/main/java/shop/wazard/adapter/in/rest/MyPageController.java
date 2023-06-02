package shop.wazard.adapter.in.rest;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.MyPageService;
import shop.wazard.dto.*;
import shop.wazard.util.aop.Certification;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-page")
class MyPageController {

    private final MyPageService myPageService;

    @Certification
    @GetMapping("/past-workplaces/{accountId}")
    public ResponseEntity<List<GetPastWorkplaceResDto>> getPastWorkplaces(
            @PathVariable Long accountId,
            @Valid @RequestBody GetPastWorkplaceReqDto getPastWorkplaceReqDto) {
        List<GetPastWorkplaceResDto> getPastWorkplaceResDtoList =
                myPageService.getPastWorkplaces(getPastWorkplaceReqDto);
        return ResponseEntity.ok(getPastWorkplaceResDtoList);
    }

    @Certification
    @GetMapping("/past-work-record/{accountId}")
    public ResponseEntity<GetMyPastWorkRecordResDto> getMyPastWorkRecord(
            @PathVariable Long accountId,
            @Valid @RequestBody GetMyPastWorkRecordReqDto getMyPastWorkRecordReqDto) {
        GetMyPastWorkRecordResDto getMyPastWorkRecordResDto =
                myPageService.getMyPastWorkingRecord(getMyPastWorkRecordReqDto);
        return ResponseEntity.ok(getMyPastWorkRecordResDto);
    }

    @Certification
    @GetMapping("/my-attitude-score/{accountId}")
    public ResponseEntity<GetMyAttitudeScoreResDto> getMyAttitudeScore(
            @PathVariable Long accountId,
            @Valid @RequestBody GetMyAttitudeScoreReqDto getMyAttitudeScoreReqDto) {
        GetMyAttitudeScoreResDto getMyAttitudeScoreResDto =
                myPageService.getMyAttitudeScore(getMyAttitudeScoreReqDto);
        return ResponseEntity.ok(getMyAttitudeScoreResDto);
    }
}
