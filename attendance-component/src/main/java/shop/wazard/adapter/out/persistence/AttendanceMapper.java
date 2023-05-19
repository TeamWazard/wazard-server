package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.application.domain.ExitRecord;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.commuteRecord.ExitRecordJpa;
import shop.wazard.entity.company.CompanyJpa;

@Component
class AttendanceMapper {

    public EnterRecordJpa toEnterRecordJpa(EnterRecord enterRecord, AccountJpa accountJpa, CompanyJpa companyJpa) {
        return EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .enterTime(enterRecord.getEnterTime())
                .tardy(enterRecord.isTardy())
                .build();
    }

    public ExitRecordJpa toExitRecordJpa(ExitRecord exitRecord, EnterRecordJpa enterRecordJpa) {
        return ExitRecordJpa.builder()
                .enterRecordJpa(enterRecordJpa)
                .exitTime(exitRecord.getExitTime())
                .build();
    }

}
