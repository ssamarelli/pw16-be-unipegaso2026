package com.pw.medicapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@OpenAPIDefinition(
        info = @Info(
                title = "MedAPP - gestionario sanitario",
                version = "1.0",
                description = "Documentazione auto-generata con swagger del backend scritto per il project work AA2025/2026"
        )
)
public class MedappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedappApplication.class, args);
	}

}
