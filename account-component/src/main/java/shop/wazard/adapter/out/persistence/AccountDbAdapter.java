package shop.wazard.adapter.out.persistence;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.domain.Account;
import shop.wazard.application.port.out.AccountPort;
import shop.wazard.entity.account.AccountJpa;
import shop.wazard.exception.AccountNotFoundException;
import shop.wazard.util.exception.StatusEnum;

@Repository
@RequiredArgsConstructor
class AccountDbAdapter implements AccountPort {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountMapper accountMapper;

    @Override
    public Boolean isPossibleEmail(String email) {
        Long accountId = accountJpaRepository.findIdByEmail(email);
        return accountId == null;
    }

    @Override
    public Account findAccountByEmail(String email) {
        AccountJpa accountJpa =
                accountJpaRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        return accountMapper.toAccountDomain(accountJpa);
    }

    @Override
    public Long findAccountIdByEmail(String email) {
        return accountJpaRepository.findIdByEmail(email);
    }

    @Override
    public Optional<Account> findAccountByEmailForUserDetails(String email) {
        AccountJpa accountJpa =
                accountJpaRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        return accountMapper.toAccountForSecurity(accountJpa);
    }

    @Override
    public void save(Account account) {
        AccountJpa accountJpa = accountMapper.toAccountJpa(account);
        accountJpaRepository.save(accountJpa);
    }

    @Override
    public void updateMyProfile(Account account) {
        AccountJpa accountJpa =
                accountJpaRepository
                        .findByEmail(account.getMyProfile().getEmail())
                        .orElseThrow(
                                () ->
                                        new AccountNotFoundException(
                                                StatusEnum.ACCOUNT_NOT_FOUND.getMessage()));
        accountMapper.updateMyProfile(accountJpa, account);
    }
}
