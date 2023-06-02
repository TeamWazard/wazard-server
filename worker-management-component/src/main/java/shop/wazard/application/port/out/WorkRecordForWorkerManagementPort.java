package shop.wazard.application.port.out;

import java.util.List;
import shop.wazard.application.domain.WorkRecordForWorkerManagement;

public interface WorkRecordForWorkerManagementPort {

    List<WorkRecordForWorkerManagement> getWorkerTotalPastRecord(Long accountId, Long companyId);
}
