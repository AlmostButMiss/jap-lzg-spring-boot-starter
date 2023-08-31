package com.example.demo;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author august
 */
@SpringBootApplication
@Slf4j
public class DemoJpaApplication {

    public static void main(String[] args) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        ConfigurableApplicationContext run = SpringApplication.run(DemoJpaApplication.class, args);

        log.info("===== 启动耗时 ====== : {}", stopwatch.stop());
    }

    @Bean
    CommandLineRunner commandLineRunner(
            RequestMappingHandlerMapping requestMappingHandlerMapping
    ) {
        return args -> {
            requestMappingHandlerMapping
                    .getHandlerMethods()
                    .forEach((requestMappingInfo, handlerMethod) -> {
                        log.info(" ========= RestController Method : [{}] " +
                                ", ========= Request uri : {}", handlerMethod.getMethod(), requestMappingInfo);
                    });
        };
    }
}
