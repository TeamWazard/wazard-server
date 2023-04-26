package shop.wazard.application.port;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import shop.wazard.adapter.out.persistence.Account;
import shop.wazard.application.port.out.LoadAccountPort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class UserDetailServiceImpl implements UserDetailsService {

    private final LoadAccountPort loadAccountPort;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = loadAccountPort.findAccountByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email : " + email + " was not found"));
        return createUserDetails(account);
    }

    private UserDetails createUserDetails(Account account) {
        List<SimpleGrantedAuthority> grantedAuthorities = account.getRoleList().stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());

        return new User(account.getEmail(),
                account.getPassword(),
                grantedAuthorities);
    }
}
