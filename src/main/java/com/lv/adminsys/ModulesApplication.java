package com.lv.adminsys;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author Qiang
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.lv.adminsys.modules.dao")
@EnableAsync
public class ModulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModulesApplication.class, args);
    }

}
