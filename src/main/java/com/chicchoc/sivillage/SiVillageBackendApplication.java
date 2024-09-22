package com.chicchoc.sivillage;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@OpenAPIDefinition(
        servers = {
                @Server(url = "https://sivillage.shop", description = "운영 서버")
        })
@EnableJpaAuditing
@SpringBootApplication
public class SiVillageBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SiVillageBackendApplication.class, args);
    }
}
