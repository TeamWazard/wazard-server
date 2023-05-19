package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.port.out.WorkerPort;

@Repository
@RequiredArgsConstructor
class WorkerDbAdapter implements WorkerPort {

    private final WorkerRepository workerRepository;
    private final WorkerMapper workerMapper;

}
