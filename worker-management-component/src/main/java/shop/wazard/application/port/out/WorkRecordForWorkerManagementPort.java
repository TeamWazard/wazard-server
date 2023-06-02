package shop.wazard.application.port.out;

import shop.wazard.application.domain.WorkRecordForWorkerManagement;

import java.util.List;

public interface WorkRecordForWorkerManagementPort {

    List<WorkRecordForWorkerManagement> getWorkerTotalPastRecord(Long accountId, Long companyId);

}
