package shop.wazard.adapter.out.persistence;

import static shop.wazard.entity.common.BaseEntity.BaseStatusJpa;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.AccountForMyPage;
import shop.wazard.application.domain.CompanyInfoForMyPage;
import shop.wazard.application.domain.WorkRecordForMyPage;
import shop.wazard.application.port.out.AccountForMyPagePort;
import shop.wazard.application.port.out.CompanyForMyPagePort;
import shop.wazard.application.port.out.RosterForMyPagePort;
import shop.wazard.application.port.out.WorkRecordForMyPagePort;
import shop.wazard.dto.GetPastWorkplaceResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.commuteRecord.EnterRecordJpa;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.exception.AccountNotFoundException;
import shop.wazard.exception.CompanyNotFoundException;
import shop.wazard.util.exception.StatusEnum;

@Repository
@RequiredArgsConstructor
class MyPageDbAdapter
        implements AccountForMyPagePort,
                CompanyForMyPagePort,
                RosterForMyPagePort,
                WorkRecordForMyPagePort {

    private final AccountJpaForMyPageRepository accountJpaForMyPageRepository;
    private final RosterJpaForMyPageRepository rosterJpaForMyPageRepository;
    private final CompanyJpaForMyPageRepository companyJpaForMyPageRepository;
    private final EnterRecordJpaForMyPageRepository enterRecordJpaForMyPageRepository;
    private final ExitRecordJpaForMyPageRepository exitRecordJpaForMyPageRepository;
    private final AbsentJpaForMyPageRepository absentJpaForMyPageRepository;
    private final AccountForMyPageMapper accountForMyPageMapper;
    private final MyPageMapper myPageMapper;

    @Override
    public AccountForMyPage findAccountByEmail(String email) {
        AccountJpa accountJpa =
                accountJpaForMyPageRepository
                        .findByEmailAndBaseStatusJpa(email, BaseStatusJpa.ACTIVE)
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        return accountForMyPageMapper.toAccountForAttendanceDomain(accountJpa);
    }

    @Override
    public List<GetPastWorkplaceResDto> getPastWorkplaces(Long accountId) {
        List<CompanyJpa> companyJpaList =
                rosterJpaForMyPageRepository.findPastWorkplacesById(accountId);
        return myPageMapper.toCompanyInfoDomainList(companyJpaList);
    }

    @Override
    public CompanyInfoForMyPage findPastCompanyInfo(Long accountId, Long companyId) {
        CompanyJpa companyJpa =
                companyJpaForMyPageRepository
                        .findPastCompanyJpaByAccountIdAndCompanyId(accountId, companyId)
                        .orElseThrow(
                                () ->
                                        new CompanyNotFoundException(
                                                StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        return myPageMapper.createCompanyInfoForMyPage(companyJpa);
    }

    @Override
    public WorkRecordForMyPage getMyPastWorkRecord(Long accountId, Long companyId) {
        return WorkRecordForMyPage.builder()
                .tardyCount(getTardyCount(accountId, companyId))
                .absentCount(getAbsentCount(accountId, companyId))
                .workDayCount(getWorkDayCount(accountId, companyId))
                .startWorkingDate(getStartWorkingDate(accountId, companyId))
                .endWorkingDate(getEndWorkingDate(accountId, companyId))
                .build();
    }

    @Override
    public List<WorkRecordForMyPage> getMyTotalPastRecord(Long accountId) {
        List<CompanyJpa> pastCompanies =
                companyJpaForMyPageRepository.findAllMyPastCompaniesByAccountId(accountId);
        return pastCompanies.stream()
                .map(x -> getMyPastWorkRecord(accountId, x.getId()))
                .collect(Collectors.toList());
    }

    private int getWorkDayCount(Long accountId, Long companyId) {
        return enterRecordJpaForMyPageRepository.countTotalWorkDayByAccountIdAndCompanyId(
                accountId, companyId);
    }

    private int getTardyCount(Long accountId, Long companyId) {
        return enterRecordJpaForMyPageRepository.countTardyByAccountIdAndCompanyId(
                accountId, companyId);
    }

    private int getAbsentCount(Long accountId, Long companyId) {
        return absentJpaForMyPageRepository.countAbsentByAccountIdAndCompanyId(
                accountId, companyId);
    }

    private LocalDate getStartWorkingDate(Long accountId, Long companyId) {
        AccountJpa accountJpa =
                accountJpaForMyPageRepository
                        .findByIdAndBaseStatusJpa(accountId, BaseStatusJpa.ACTIVE)
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa =
                companyJpaForMyPageRepository
                        .findByIdAndBaseStatusJpa(companyId, BaseStatusJpa.ACTIVE)
                        .orElseThrow(
                                () ->
                                        new CompanyNotFoundException(
                                                StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        return enterRecordJpaForMyPageRepository
                .findTopByAccountJpaAndCompanyJpaAndBaseStatusJpaOrderByIdAsc(
                        accountJpa, companyJpa, BaseStatusJpa.ACTIVE)
                .getEnterDate();
    }

    private LocalDate getEndWorkingDate(Long accountId, Long companyId) {
        AccountJpa accountJpa =
                accountJpaForMyPageRepository
                        .findByIdAndBaseStatusJpa(accountId, BaseStatusJpa.ACTIVE)
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        CompanyJpa companyJpa =
                companyJpaForMyPageRepository
                        .findByIdAndBaseStatusJpa(companyId, BaseStatusJpa.ACTIVE)
                        .orElseThrow(
                                () ->
                                        new CompanyNotFoundException(
                                                StatusEnum.COMPANY_NOT_FOUND.getMessage()));
        EnterRecordJpa enterRecordJpa =
                enterRecordJpaForMyPageRepository
                        .findTopByAccountJpaAndCompanyJpaAndBaseStatusJpaOrderByIdDesc(
                                accountJpa, companyJpa, BaseStatusJpa.ACTIVE);
        return exitRecordJpaForMyPageRepository
                .findTopByEnterRecordJpaAndBaseStatusJpaOrderByIdDesc(
                        enterRecordJpa, BaseStatusJpa.ACTIVE)
                .getExitDate();
    }
}
