package shop.wazard.adapter.in.rest;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.WorkerManagementService;
import shop.wazard.dto.*;
import shop.wazard.util.aop.Certification;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker-management")
class WorkerManagementController {

    private final WorkerManagementService workerManagementService;

    @Certification
    @PostMapping("/permit/{accountId}")
    public ResponseEntity<PermitWorkerToJoinResDto> permitWorkerToJoin(
            @PathVariable Long accountId,
            @Valid @RequestBody PermitWorkerToJoinReqDto permitWorkerToJoinReqDto) {
        PermitWorkerToJoinResDto permitWorkerToJoinResDto =
                workerManagementService.permitWorkerToJoin(permitWorkerToJoinReqDto);
        return ResponseEntity.ok(permitWorkerToJoinResDto);
    }

    @Certification
    @GetMapping("/workers/{accountId}")
    public ResponseEntity<List<WorkerBelongedToCompanyResDto>> getWorkersBelongedCompany(
            @PathVariable Long accountId,
            @RequestParam Long companyId,
            @Valid @RequestBody WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto) {
        List<WorkerBelongedToCompanyResDto> workerBelongedToCompanyResDtoList =
                workerManagementService.getWorkersBelongedCompany(
                        companyId, workerBelongedToCompanyReqDto);
        return ResponseEntity.ok(workerBelongedToCompanyResDtoList);
    }

    @Certification
    @PatchMapping("/out/{accountId}")
    public ResponseEntity<ExileWorkerResDto> exileWorker(
            @PathVariable Long accountId, @Valid @RequestBody ExileWorkerReqDto exileWorkerReqDto) {
        ExileWorkerResDto exileWorkerResDto =
                workerManagementService.exileWorker(exileWorkerReqDto);
        return ResponseEntity.ok(exileWorkerResDto);
    }

    @Certification
    @GetMapping("/workers/waiting/{accountId}")
    public ResponseEntity<List<WaitingWorkerResDto>> getWaitingWorkers(
            @PathVariable Long accountId,
            @RequestParam Long companyId,
            @Valid @RequestBody WaitingWorkerReqDto waitingWorkerReqDto) {
        List<WaitingWorkerResDto> waitingWorkerList =
                workerManagementService.getWaitingWorkers(companyId, waitingWorkerReqDto);
        return ResponseEntity.ok(waitingWorkerList);
    }

    @Certification
    @GetMapping("/attendance/{accountId}/{year}/{month}")
    public ResponseEntity<GetWorkerAttendanceRecordResDto> getWorkerAttendanceRecord(
            @PathVariable Long accountId,
            @PathVariable int year,
            @PathVariable int month,
            @Valid GetWorkerAttendanceRecordReqDto getWorkerAttendanceRecordReqDto) {
        GetWorkerAttendanceRecordResDto getWorkerAttendanceRecordResDto =
                workerManagementService.getWorkerAttendanceRecord(
                        getWorkerAttendanceRecordReqDto, year, month);
        return ResponseEntity.ok(getWorkerAttendanceRecordResDto);
    }

    @Certification
    @GetMapping("/workers/replace/{accountId}")
    public ResponseEntity<List<GetAllReplaceRecordResDto>> getAllReplaceRecord(@PathVariable Long accountId, @Valid @RequestBody GetAllReplaceRecordReqDto getAllReplaceRecordReqDto) {
        List<GetAllReplaceRecordResDto> getAllReplaceRecordResDtoList = workerManagementService.getAllReplaceRecord(getAllReplaceRecordReqDto);
        return ResponseEntity.ok(getAllReplaceRecordResDtoList);
    }
  
    @GetMapping("/worker-attitude-score/{accountId}")
    public ResponseEntity<GetWorkerAttitudeScoreResDto> getWorkerAttitudeScore(
            @PathVariable Long accountId,
            @Valid @RequestBody GetWorkerAttitudeScoreReqDto getWorkerAttitudeScoreReqDto) {
        GetWorkerAttitudeScoreResDto getWorkerAttitudeScoreResDto =
                workerManagementService.getWorkerAttitudeScore(getWorkerAttitudeScoreReqDto);
        return ResponseEntity.ok(getWorkerAttitudeScoreResDto);
    }
}