package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.application.domain.ExitRecord;
import shop.wazard.dto.GetAttendanceByDayOfTheWeekResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.commuteRecord.ExitRecordJpa;
import shop.wazard.entity.company.CompanyJpa;

import java.util.List;
import java.util.stream.Collectors;

@Component
class AttendanceMapper {

    public EnterRecordJpa toEnterRecordJpa(EnterRecord enterRecord, AccountJpa accountJpa, CompanyJpa companyJpa) {
        return EnterRecordJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .enterTime(enterRecord.getEnterTime())
                .enterDate(enterRecord.getEnterDate())
                .tardy(enterRecord.isTardy())
                .build();
    }

    public ExitRecordJpa toExitRecordJpa(ExitRecord exitRecord, EnterRecordJpa enterRecordJpa) {
        return ExitRecordJpa.builder()
                .enterRecordJpa(enterRecordJpa)
                .exitTime(exitRecord.getExitTime())
                .build();
    }

    public List<GetAttendanceByDayOfTheWeekResDto> getAttendancesByDayOfTheWeek(List<EnterRecordJpa> enterRecordJpaList) {
        return enterRecordJpaList.stream()
                .map(enterRecord -> GetAttendanceByDayOfTheWeekResDto.builder()
                        .accountId(enterRecord.getId())
                        .userName(enterRecord.getAccountJpa().getUserName())
                        .enterTime(enterRecord.getEnterTime())
                        .exitTime(enterRecord.getExitRecordJpa().getExitTime())
                        .build())
                .collect(Collectors.toList());
    }

}
