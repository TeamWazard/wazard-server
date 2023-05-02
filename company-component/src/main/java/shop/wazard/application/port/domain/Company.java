package shop.wazard.application.port.domain;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class Company {

    private Long id;
    private CompanyInfo companyInfo;

}
