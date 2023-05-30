package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.MyPageService;
import shop.wazard.dto.GetMyPastWorkRecordReqDto;
import shop.wazard.dto.GetMyPastWorkRecordResDto;
import shop.wazard.dto.GetPastWorkplaceReqDto;
import shop.wazard.dto.GetPastWorkplaceResDto;
import shop.wazard.util.aop.Certification;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/my-page")
class MyPageController {

    private final MyPageService myPageService;

    @Certification
    @GetMapping("/past-workplaces/{accountId}")
    public ResponseEntity<List<GetPastWorkplaceResDto>> getPastWorkplaces(@PathVariable Long accountId, @Valid @RequestBody GetPastWorkplaceReqDto getPastWorkplaceReqDto) {
        List<GetPastWorkplaceResDto> getPastWorkplaceResDtoList = myPageService.getPastWorkplaces(getPastWorkplaceReqDto);
        return ResponseEntity.ok(getPastWorkplaceResDtoList);
    }

    @Certification
    @GetMapping("/past-work-record/{accountId}")
    public ResponseEntity<GetMyPastWorkRecordResDto> getMyPastWorkRecord(@PathVariable Long accountId, @Valid @RequestBody GetMyPastWorkRecordReqDto getMyPastWorkRecordReqDto) {
        GetMyPastWorkRecordResDto getMyPastWorkRecordResDto = myPageService.getMyPastWorkingRecord(getMyPastWorkRecordReqDto);
        return ResponseEntity.ok(getMyPastWorkRecordResDto);
    }

}
