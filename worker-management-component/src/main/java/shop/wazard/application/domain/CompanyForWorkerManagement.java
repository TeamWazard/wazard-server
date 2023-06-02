package shop.wazard.application.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompanyForWorkerManagement {

    private Long id;
    private CompanyInfo companyInfo;
}
