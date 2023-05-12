package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.WorkerManagementService;
import shop.wazard.dto.PermitWorkerToJoinReqDto;
import shop.wazard.dto.PermitWorkerToJoinResDto;
import shop.wazard.dto.WorkerBelongedToCompanyReqDto;
import shop.wazard.dto.WorkerBelongedToCompanyResDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker-management")
class WorkerManagementController {

    private final WorkerManagementService workerManagementService;

    @PostMapping("/permit/{accountId}")
    public ResponseEntity<PermitWorkerToJoinResDto> permitWorkerToJoin(@PathVariable Long accountId, @Valid @RequestBody PermitWorkerToJoinReqDto permitWorkerToJoinReqDto) {
        PermitWorkerToJoinResDto permitWorkerToJoinResDto = workerManagementService.permitWorkerToJoin(permitWorkerToJoinReqDto);
        return ResponseEntity.ok(permitWorkerToJoinResDto);
    }

    @GetMapping("/workers/{accountId}")
    public ResponseEntity<List<WorkerBelongedToCompanyResDto>> getWorkersBelongedCompany(@PathVariable Long accountId, @Valid @RequestBody WorkerBelongedToCompanyReqDto workerBelongedToCompanyReqDto) {
        List<WorkerBelongedToCompanyResDto> workerBelongedToCompanyResDtoList = workerManagementService.getWorkersBelongedCompany(workerBelongedToCompanyReqDto);
        return ResponseEntity.ok(workerBelongedToCompanyResDtoList);
    }

}
