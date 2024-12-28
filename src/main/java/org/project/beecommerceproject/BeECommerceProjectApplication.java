package org.project.beecommerceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "org.project.beecommerceproject")
//@ComponentScan(basePackages = "org.project.beecommerceproject")
public class BeECommerceProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeECommerceProjectApplication.class, args);
    }

}
