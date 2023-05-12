package shop.wazard.application.port.out;

import shop.wazard.application.domain.RosterForWorkerManagement;
import shop.wazard.dto.WorkerBelongedToCompanyResDto;

import java.util.List;

public interface RosterForWorkerManagementPort {

    void joinWorker(RosterForWorkerManagement rosterForWorkerManagement);

    List<WorkerBelongedToCompanyResDto> getWorkersBelongedToCompany(Long companyId);

}
