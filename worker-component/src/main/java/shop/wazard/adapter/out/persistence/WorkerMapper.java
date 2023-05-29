package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.ReplaceInfo;
import shop.wazard.dto.GetMyReplaceResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.entity.worker.ReplaceWorkerJpa;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<GetMyReplaceResDto> toMyReplace(List<ReplaceWorkerJpa> replaceWorkerJpaList) {
        return replaceWorkerJpaList.stream()
                .map(ReplaceWorkerJpa -> GetMyReplaceResDto.builder()
                        .userName(ReplaceWorkerJpa.getAccountJpa().getUserName())
                        .replaceWorkerName(ReplaceWorkerJpa.getReplaceWorkerName())
                        .replaceDate(ReplaceWorkerJpa.getReplaceDate())
                        .enterTime(ReplaceWorkerJpa.getEnterTime())
                        .exitTime(ReplaceWorkerJpa.getExitTime())
                        .build()
                ).collect(Collectors.toList());
    }

}
