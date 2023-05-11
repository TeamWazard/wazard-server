package shop.wazard.application.port.out;

public interface WorkerManagementPort {

    void updateWorkerWaitingStatus(Long accountId, Long companyId);

}
