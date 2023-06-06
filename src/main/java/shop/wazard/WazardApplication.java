package shop.wazard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class WazardApplication {

    public static void main(String[] args) {
        SpringApplication.run(WazardApplication.class, args);
    }
}
