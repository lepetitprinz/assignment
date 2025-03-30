package com.musinsa.assignment.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "client")
@Setter
@Getter
public class CustomProperties {
    private Kafka kafka;

    @Setter
    @Getter
    public static class Kafka {
        private String topics;
        private Dlq dlq = new Dlq();
        private BackOff backOff = new BackOff();
    }

    @Setter
    @Getter
    public static class Dlq {
        private String topics;
    }

    @Setter
    @Getter
    public static class BackOff {
        private Integer interval;
        private Integer maxFailure;
    }
}
