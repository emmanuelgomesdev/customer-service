package com.emmanuel.customerservice.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){

        return new OpenAPI()
                .info(new Info()
                        .title("Customer API")
                        .description("REST API for customer management")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Emmanuel Gomes")
                                .email("emmanueu@gmail.com")
                                .url("https://github.com/EmmanuelGomesSilva")));
    }
}


