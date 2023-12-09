package com.fmi.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value="test")
class DemoApplicationTests extends BaseTest {

    @Test
    void contextLoads() {
    }

}
