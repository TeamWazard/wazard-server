package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.CommuteRecordForAttendance;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.commuteRecord.CommuteRecordJpa;
import shop.wazard.entity.company.CompanyJpa;

@Component
class AttendanceMapper {

    public CommuteRecordJpa toCommuteRecordJpa(CommuteRecordForAttendance commuteRecordForAttendance, AccountJpa accountJpa, CompanyJpa companyJpa) {
        return CommuteRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .commuteTypeJpa(CommuteTypeJpa.valueOf(commuteRecordForAttendance.getCommuteType().commuteType))
                .commuteTime(commuteRecordForAttendance.getCommuteTime())
                .tardy(commuteRecordForAttendance.isTardy())
                .build();
    }

}
