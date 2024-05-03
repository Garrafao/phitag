package de.garrafao.phitag;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "phitag")
@Validated
public class ApplicationConfiguration {
    public ApplicationConfiguration() {
    }
}