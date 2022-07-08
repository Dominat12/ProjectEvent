package com.example.eventpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EventPoCApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventPoCApplication.class, args);
    }

}
