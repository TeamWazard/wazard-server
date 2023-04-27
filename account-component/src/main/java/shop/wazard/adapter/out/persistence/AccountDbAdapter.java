package shop.wazard.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.wazard.application.port.out.LoadAccountPort;
import shop.wazard.application.port.out.SaveAccountPort;
import shop.wazard.application.port.out.UpdateAccountPort;
import shop.wazard.dto.JoinReqDto;
import shop.wazard.util.exception.StatusEnum;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class AccountDbAdapter implements LoadAccountPort, SaveAccountPort, UpdateAccountPort {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountDomainConverter accountDomainConverter;

    @Override
    public void doubleCheckEmail(String email) {
        Long accountId = accountJpaRepository.findIdByEmail(email);
        if (accountId != null) {
            throw new IllegalArgumentException(StatusEnum.IS_EXIST_ACCOUNT.getMessage());
        }
    }

    @Override
    public Optional<Account> findAccountByEmail(String email) {
        return accountJpaRepository.findByEmail(email);
    }

    @Override
    public Long findAccountIdByEmail(String email) {
        return accountJpaRepository.findIdByEmail(email);
    }

    @Override
    public void save(JoinReqDto joinReqDto) {
        Account account = accountDomainConverter.joinReqDtoToAccount(joinReqDto);
        accountJpaRepository.save(account);
    }

}
