package shop.wazard.application.port.out;

import shop.wazard.application.domain.RosterForWorkerManagement;
import shop.wazard.dto.WaitingWorkerResDto;
import shop.wazard.dto.WorkerBelongedToCompanyResDto;

import java.util.List;

public interface RosterForWorkerManagementPort {

    void joinWorker(RosterForWorkerManagement rosterForWorkerManagement);

    List<WorkerBelongedToCompanyResDto> getWorkersBelongedToCompany(Long companyId);

    RosterForWorkerManagement findRoster(Long accountId, Long companyId);

    void exileWorker(RosterForWorkerManagement rosterForWorkerManagement);

    List<WaitingWorkerResDto> getWaitingWorker(Long companyId);

}
