package shop.wazard.adapter.out.persistence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.wazard.entity.account.AccountJpa;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@EnableJpaRepositories(basePackages = {"shop.wazard.*"})
@EntityScan(basePackages = "shop.wazard.entity.*")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {AccountDbAdapter.class, AccountJpaRepository.class, AccountMapper.class})
class AccountDbAdapterTest {
    @Autowired
    private AccountJpaRepository accountJpaRepository;
    @Autowired
    private AccountMapper accountMapper;


    @Test
    @DisplayName("test")
    public void test() throws Exception {
        // given
        Optional<AccountJpa> byEmail = accountJpaRepository.findByEmail("ffff@email.com");
        // when

        // then
    }


}
