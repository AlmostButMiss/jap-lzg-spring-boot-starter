package com.example.demo;

import com.example.demo.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoJpaApplicationTests {


    @Autowired
    private TestService testService;
    @Test
    void contextLoads() {
        System.out.println(testService.echo());
    }

}
