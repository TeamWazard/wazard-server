package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.AccountForMyPage;
import shop.wazard.application.port.out.AccountForMyPagePort;
import shop.wazard.application.port.out.CompanyForMyPagePort;
import shop.wazard.application.port.out.RosterForMyPagePort;
import shop.wazard.dto.GetPastWorkplaceResDto;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.entity.common.BaseEntity;
import shop.wazard.entity.company.CompanyJpa;
import shop.wazard.exception.AccountNotFoundException;
import shop.wazard.util.exception.StatusEnum;

import java.util.List;

@Repository
@RequiredArgsConstructor
class MyPageDbAdapter implements AccountForMyPagePort, CompanyForMyPagePort, RosterForMyPagePort {

    private final AccountJpaForMyPageRepository accountJpaForMyPageRepository;
    private final RosterJpaForMyPageRepository rosterJpaForMyPageRepository;
    private final CompanyJpaForMyPageRepository companyJpaForMyPageRepository;
    private final AccountForMyPageMapper accountForMyPageMapper;
    private final MyPageMapper myPageMapper;

    @Override
    public AccountForMyPage findAccountByEmail(String email) {
        AccountJpa accountJpa = accountJpaForMyPageRepository.findByEmailAndBaseStatusJpa(email, BaseEntity.BaseStatusJpa.ACTIVE)
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        return accountForMyPageMapper.toAccountForAttendanceDomain(accountJpa);
    }

    @Override
    public List<GetPastWorkplaceResDto> getPastWorkplaces(Long accountId) {
        List<CompanyJpa> companyJpaList = rosterJpaForMyPageRepository.findPastWorkplacesById(accountId);
        return myPageMapper.toCompanyInfoDomainList(companyJpaList);
    }

}