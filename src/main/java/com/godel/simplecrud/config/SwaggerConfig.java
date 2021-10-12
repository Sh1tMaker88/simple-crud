package com.godel.simplecrud.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    //todo actualize documentation
    @Bean
    public OpenAPI myOpenApi() {
        Info info = new Info()
                .title("CRUD operations with employees")
                .version("1.0.0")
                .contact(new Contact()
                        .email("25mugl@gmail.com")
                        .name("Pysko Vadim"))
                .description("Test project for Godel Technologies");

        return new OpenAPI()
                .info(info);
    }
}
