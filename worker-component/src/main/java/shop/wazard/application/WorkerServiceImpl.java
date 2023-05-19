package shop.wazard.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.wazard.application.port.in.WorkerService;
import shop.wazard.application.port.out.WorkerPort;

@Transactional
@Service
@RequiredArgsConstructor
class WorkerServiceImpl implements WorkerService {

    private final WorkerPort workerPort;

}
