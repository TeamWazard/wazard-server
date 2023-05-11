package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.wazard.application.port.in.WorkerManagementService;
import shop.wazard.dto.PermitWorkerToJoinReqDto;
import shop.wazard.dto.PermitWorkerToJoinResDto;

import javax.validation.Valid;

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

}
