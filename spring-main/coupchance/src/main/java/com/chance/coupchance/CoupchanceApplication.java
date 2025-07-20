package com.chance.coupchance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EntityScan(basePackages = "com.chance.coupchance.Entites")
@ComponentScan(basePackages = {"com.chance.coupchance", "com.chance.Security"})
public class CoupchanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoupchanceApplication.class, args);
    }
}
