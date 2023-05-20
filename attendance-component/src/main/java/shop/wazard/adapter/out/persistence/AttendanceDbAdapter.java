package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.AbsentForAttendance;
import shop.wazard.application.domain.AccountForAttendance;
import shop.wazard.application.domain.Attendance;
import shop.wazard.application.domain.EnterRecord;
import shop.wazard.application.port.out.AbsentForAttendancePort;
import shop.wazard.application.port.out.AccountForAttendancePort;
import shop.wazard.application.port.out.CommuteRecordForAttendancePort;
import shop.wazard.dto.GetAttendanceResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.commuteRecord.AbsentJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.exception.AccountNotFoundException;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.util.exception.StatusEnum;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
class AttendanceDbAdapter implements AccountForAttendancePort, CommuteRecordForAttendancePort, AbsentForAttendancePort {

    private final AttendanceMapper attendanceMapper;
    private final AccountForAttendanceMapper accountForAttendanceMapper;
    private final AccountJpaForAttendanceRepository accountJpaForAttendanceRepository;
    private final CompanyJpaForAttendanceRepository companyJpaForAttendanceRepository;
    private final AbsentJpaForAttendanceRepository absentJpaForAttendanceRepository;
    private final EnterRecordJpaForAttendanceRepository enterRecordJpaForAttendanceRepository;
    private final ExitRecordJpaForAttendanceRepository exitRecordJpaForAttendanceRepository;
    private final EntityManager em;

    @Override
    public AccountForAttendance findAccountByEmail(String email) {
        AccountJpa accountJpa = accountJpaForAttendanceRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        return accountForAttendanceMapper.toAccountForAttendanceDomain(accountJpa);
    }

    @Override
    public void markingAbsent(AbsentForAttendance absentForAttendance) {
        AccountJpa accountJpa = accountJpaForAttendanceRepository.findById(absentForAttendance.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa = companyJpaForAttendanceRepository.findById(absentForAttendance.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        absentJpaForAttendanceRepository.save(
                AbsentJpa.builder()
                .accountJpa(accountJpa)
                .companyJpa(companyJpa)
                .absentDate(LocalDate.now())
                .build());
    }

    @Override
    public void recordEnterTime(EnterRecord enterRecord) {
        AccountJpa accountJpa = accountJpaForAttendanceRepository.findById(enterRecord.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa = companyJpaForAttendanceRepository.findById(enterRecord.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        EnterRecordJpa enterRecordJpa = attendanceMapper.toEnterRecordJpa(enterRecord, accountJpa, companyJpa);
        enterRecordJpaForAttendanceRepository.save(enterRecordJpa);
    }

    @Override
    public List<GetAttendanceResDto> getAttendancesByDayOfTheWeek(Attendance attendance) {
        CompanyJpa companyJpa = companyJpaForAttendanceRepository.findById(attendance.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        List<EnterRecordJpa> enterRecordJpaList = enterRecordJpaForAttendanceRepository.findAllByCompanyJpaAndEnterDateOrderByAccountJpaAsc(companyJpa, attendance.getEnterDate());
        return attendanceMapper.getAllAttendances(enterRecordJpaList);
    }
}
