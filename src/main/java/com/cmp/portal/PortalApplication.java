package com.cmp.portal;

import com.cmp.portal.common.SessionCounter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class PortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }

    @Bean
    public SessionCounter sessionCounter() {
        return new SessionCounter();
    }
}
