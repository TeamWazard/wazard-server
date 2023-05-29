package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.worker.ReplaceWorkerJpa;

@Component
class WorkerMapper {
    public ReplaceWorkerJpa saveReplaceInfo(AccountJpa accountJpa, CompanyJpa companyJpa, ReplaceInfo replaceInfo) {
        return ReplaceWorkerJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .replaceWorkerName(replaceInfo.getReplaceWorkerName())
                .replaceDate(replaceInfo.getReplaceDate())
                .enterTime(replaceInfo.getEnterTime())
                .exitTime(replaceInfo.getExitTime())
                .build();
    }
}
