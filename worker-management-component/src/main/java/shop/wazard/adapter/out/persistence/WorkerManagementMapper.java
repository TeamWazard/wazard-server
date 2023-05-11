package shop.wazard.adapter.out.persistence;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import shop.wazard.application.domain.AccountForWorkerManagement;
import shop.wazard.application.domain.CompanyForWorkerManagement;
import shop.wazard.application.domain.CompanyInfo;
import shop.wazard.application.domain.WaitingInfo;
import shop.wazard.entity.company.WaitingListJpa;

@Component
@Slf4j
class WorkerManagementMapper {

    public WaitingInfo toWaitingInfoDomain(WaitingListJpa waitingListJpa) {
        return WaitingInfo.builder()
                .company(CompanyForWorkerManagement.builder()
                        .id(waitingListJpa.getCompanyJpa().getId())
                        .companyInfo(
                                CompanyInfo.builder()
                                        .companyName(waitingListJpa.getCompanyJpa().getCompanyName())
                                        .companyAddress(waitingListJpa.getCompanyJpa().getCompanyAddress())
                                        .companyContact(waitingListJpa.getCompanyJpa().getCompanyContact())
                                        .salaryDate(waitingListJpa.getCompanyJpa().getSalaryDate())
                                        .logoImageUrl(waitingListJpa.getCompanyJpa().getLogoImageUrl())
                                        .build()
                        ))
                .account(AccountForWorkerManagement.builder()
                        .id(waitingListJpa.getAccountJpa().getId())
                        .email(waitingListJpa.getAccountJpa().getEmail())
                        .userName(waitingListJpa.getAccountJpa().getUserName())
                        .roles(waitingListJpa.getAccountJpa().getRoles())
                        .build())
                .waitingStatusJpa(waitingListJpa.getWaitingStatusJpa().getStatus())
                .build();
    }
}
