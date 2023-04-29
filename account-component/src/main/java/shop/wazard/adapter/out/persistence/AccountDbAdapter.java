package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import shop.wazard.application.port.domain.Account;
import shop.wazard.application.port.out.LoadAccountPort;
import shop.wazard.application.port.out.SaveAccountPort;
import shop.wazard.application.port.out.UpdateAccountPort;
import shop.wazard.exception.AccountNotFoundException;
import shop.wazard.util.exception.StatusEnum;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
class AccountDbAdapter implements LoadAccountPort, SaveAccountPort, UpdateAccountPort {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountMapper accountMapper;

    @Override
    public Boolean isPossibleEmail(String email) {
        Long accountId = accountJpaRepository.findIdByEmail(email);
        return accountId == null;
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) {
        AccountJpa accountJpa = accountJpaRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        log.info("{}", accountJpa.getAccountStatusJpa());
        return accountMapper.toAccountDomain(accountJpa);
    }

    @Override
    public Long findAccountIdByEmail(String email) {
        return accountJpaRepository.findIdByEmail(email);
    }

    @Override
    public Optional<Account> findAccountByEmailForUserDetails(String email) {
        AccountJpa accountJpa = accountJpaRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        return accountMapper.toAccountForSecurity(accountJpa);
    }

    @Override
    public void save(Account account) {
        AccountJpa accountJpa = accountMapper.toAccountJpa(account);
        accountJpaRepository.save(accountJpa);
    }

    @Override
    public void updateMyProfile(Account account) {
        log.info("====== update {}======", account.getMyProfile().getPhoneNumber());
        AccountJpa accountJpa = accountJpaRepository.findByEmail(account.getMyProfile().getEmail())
                .orElseThrow(() -> new AccountNotFoundException(StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        accountJpa.updateMyProfile(account);
    }

}
