package com.aha.tech;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class,
        value = {"spring.cloud.nacos.discovery.register-enabled=false",
                "spring.cloud.nacos.discovery.watch.enabled=false",
                "logging.level.root=info"})
public abstract class MemberApplicationTests {


}

