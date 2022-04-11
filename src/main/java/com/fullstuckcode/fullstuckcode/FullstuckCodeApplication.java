package com.fullstuckcode.fullstuckcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "FullstuckCode",
                version = "1.0.0",
                description = "FullstuckCode API",
                license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        name = "FullstuckCode",
                        url = "https://fullstuckcode.com",
                        email = "mail@fullstuck.com"
                )
        )
)
public class FullstuckCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullstuckCodeApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
