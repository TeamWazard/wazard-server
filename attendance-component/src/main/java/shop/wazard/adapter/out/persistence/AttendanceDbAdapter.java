package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.AbsentForAttendance;
import shop.wazard.application.domain.AccountForAttendance;
import shop.wazard.application.domain.CommuteRecordForAttendance;
import shop.wazard.application.port.out.AbsentForAttendancePort;
import shop.wazard.application.port.out.AccountForAttendancePort;
import shop.wazard.application.port.out.CommuteRecordForAttendancePort;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.commuteRecord.AbsentJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.exception.AccountNotFoundException;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.util.exception.StatusEnum;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
class AttendanceDbAdapter implements AccountForAttendancePort, CommuteRecordForAttendancePort, AbsentForAttendancePort {

    private final AttendanceMapper attendanceMapper;
    private final AccountForAttendanceMapper accountForAttendanceMapper;
    private final AccountJpaForAttendanceRepository accountJpaForAttendanceRepository;
    private final CompanyJpaForAttendanceRepository companyJpaForAttendanceRepository;
    private final AbsentJpaForAttendanceRepository absentJpaForAttendanceRepository;
    private final CommuteRecordJpaForAttendanceRepository commuteRecordJpaForAttendanceRepository;

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
    public void recordCommute(CommuteRecordForAttendance commuteRecordForAttendance) {

    }
}