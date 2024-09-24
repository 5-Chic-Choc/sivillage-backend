package com.chicchoc.sivillage;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

//@OpenAPIDefinition(servers = {
//    @Server(url = "https://sivillage.shop", description = "운영 서버"),
//    @Server(url = "http://localhost:8080", description = "로컬 서버")
//})

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
public class SiVillageBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiVillageBackendApplication.class, args);
    }
}