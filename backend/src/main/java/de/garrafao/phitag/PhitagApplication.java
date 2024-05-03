package de.garrafao.phitag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@SpringBootApplication()
@EnableConfigurationProperties(ApplicationConfiguration.class)
public class PhitagApplication implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhitagApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PhitagApplication.class, args);
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        LOGGER.info("====================");
        LOGGER.info("System started");
        LOGGER.info("====================");
    }
}