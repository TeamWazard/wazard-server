package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.company.CompanyJpa;

@Component
class AttendanceMapper {

    public EnterRecordJpa toEnterRecordJpa(EnterRecord enterRecord, AccountJpa accountJpa, CompanyJpa companyJpa) {
        return EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .enterTime(enterRecord.getCommuteTime())
                .tardy(enterRecord.isTardy())
                .build();
    }

}
