package com.gregoryan.api.Models;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
            .info(new Info()
                .title("Gregorian - Gestão de Atedimentos")
                .version("0.0.3")
                .description("API da Aplicação Gregorian para gestão de atendimento clínicos")

            );
    }
}
