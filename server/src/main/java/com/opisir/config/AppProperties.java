package com.opisir.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Auther: dingjn
 * @Desc:
 */
@Configuration
@ConfigurationProperties(prefix = "opisir")
public class AppProperties {

    @Getter
    @Setter
    private Jwt jwt = new Jwt();

    @Getter
    @Setter
    public static class Jwt {
        private String header;
        private String secret;
        private Long expired;
        private String prefix;
    }
}
