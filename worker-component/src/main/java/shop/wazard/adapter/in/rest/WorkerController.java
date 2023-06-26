package shop.wazard.adapter.in.rest;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.WorkerService;
import shop.wazard.dto.*;
import shop.wazard.util.aop.Certification;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker")
class WorkerController {

    private final WorkerService workerService;

    @Certification
    @PostMapping("/replace/register/{accountId}")
    public ResponseEntity<RegisterReplaceResDto> registerReplace(
            @PathVariable Long accountId,
            @Valid @RequestBody RegisterReplaceReqDto registerReplaceReqDto) {
        RegisterReplaceResDto registerReplaceResDto =
                workerService.registerReplace(registerReplaceReqDto);
        return ResponseEntity.ok(registerReplaceResDto);
    }

    @Certification
    @GetMapping("/replace/{accountId}")
    public ResponseEntity<List<GetMyReplaceRecordResDto>> getMyReplace(
            @PathVariable Long accountId,
            @RequestParam Long companyId,
            @Valid @RequestBody GetMyReplaceRecordReqDto getMyReplaceRecordReqDto) {
        List<GetMyReplaceRecordResDto> getMyReplaceRecordResDtoList =
                workerService.getMyReplaceRecord(getMyReplaceRecordReqDto, companyId);
        return ResponseEntity.ok(getMyReplaceRecordResDtoList);
    }

    @Certification
    @GetMapping("/contract/{accountId}")
    public ResponseEntity<GetEarlyContractInfoResDto> getEarlyContractInfo(
            @PathVariable Long accountId,
            @Valid @RequestBody GetEarlyContractInfoReqDto getEarlyContractInfoReqDto) {
        GetEarlyContractInfoResDto getEarlyContractInfoResDto =
                workerService.getEarlyContractInfo(getEarlyContractInfoReqDto);
        return ResponseEntity.ok(getEarlyContractInfoResDto);
    }

    @PatchMapping("/contract")
    public ResponseEntity<CheckAgreementResDto> modifyContractAgreement(
            @Valid @RequestBody CheckAgreementReqDto checkAgreementReqDto) {
        CheckAgreementResDto checkAgreementResDto =
                workerService.checkAgreement(checkAgreementReqDto);
        return ResponseEntity.ok(checkAgreementResDto);
    }
}
