package shop.wazard.adapter.out.persistence;

import org.springframework.stereotype.Component;
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.dto.GetAttendanceResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.company.CompanyJpa;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<GetAttendanceResDto> getAllAttendances(List<EnterRecordJpa> enterRecordJpaList) {
        return enterRecordJpaList.stream()
                .map(enterRecord -> GetAttendanceResDto.builder()
                        .accountId(enterRecord.getId())
                        .userName(enterRecord.getAccountJpa().getUserName())
                        .enterTime(enterRecord.getEnterTime())
                        .exitTime(enterRecord.getExitRecordJpa().getExitTime())
                        .build())
                .collect(Collectors.toList());
    }

}
