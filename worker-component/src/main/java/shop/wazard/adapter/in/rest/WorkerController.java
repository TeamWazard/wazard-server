package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.WorkerService;
import shop.wazard.dto.GetMyReplaceRecordReqDto;
import shop.wazard.dto.GetMyReplaceRecordResDto;
import shop.wazard.dto.RegisterReplaceReqDto;
import shop.wazard.dto.RegisterReplaceResDto;
import shop.wazard.util.aop.Certification;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
class WorkerController {

    private final WorkerService workerService;

    @Certification
    @PostMapping("/replace/register/{accountId}")
    public ResponseEntity<RegisterReplaceResDto> registerReplace(@PathVariable Long accountId, @Valid @RequestBody RegisterReplaceReqDto registerReplaceReqDto) {
        RegisterReplaceResDto registerReplaceResDto = workerService.registerReplace(registerReplaceReqDto);
        return ResponseEntity.ok(registerReplaceResDto);
    }

    @Certification
    @GetMapping("/replace/{accountId{")
    public ResponseEntity<List<GetMyReplaceRecordResDto>> getMyReplace(@PathVariable Long accountId, @Valid @RequestBody GetMyReplaceRecordReqDto getMyReplaceRecordReqDto) {
        List<GetMyReplaceRecordResDto> getMyReplaceRecordResDtoList = workerService.getMyReplaceRecord(getMyReplaceRecordReqDto);
        return ResponseEntity.ok(getMyReplaceRecordResDtoList);
    }

}