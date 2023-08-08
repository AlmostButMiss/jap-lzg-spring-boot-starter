package com.lzg.jpa.config;

import com.lzg.jpa.repository.impl.BaseRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author : liuzg
 * @description todo
 * @date : 2023-07-31 10:45
 * @since 1.0
 **/
@Configuration
@EnableJpaRepositories(basePackages = "com.*", repositoryBaseClass = BaseRepositoryImpl.class)
@EntityScan(basePackages = "com.*")
@ComponentScan(basePackages = "com.*")
public class JpaConfig {

}
