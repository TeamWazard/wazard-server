package shop.wazard.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.wazard.application.port.in.WorkerManagementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/worker-management")
class WorkerManagementController {

    private final WorkerManagementService workerManagementService;

}
