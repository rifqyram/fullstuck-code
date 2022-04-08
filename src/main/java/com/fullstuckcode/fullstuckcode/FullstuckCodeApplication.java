package com.fullstuckcode.fullstuckcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SpringBootApplication
public class FullstuckCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullstuckCodeApplication.class, args);
    }

}
