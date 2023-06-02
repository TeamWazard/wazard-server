package shop.wazard.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import shop.wazard.WazardApplication;

@SpringBootTest
@ContextConfiguration(classes = WazardApplication.class)
class WazardApplicationTests {

    @Test
    void contextLoads() {}
}
