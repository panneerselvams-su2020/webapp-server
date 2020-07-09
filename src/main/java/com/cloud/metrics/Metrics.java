package com.cloud.metrics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.timgroup.statsd.NoOpStatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

@Configuration
public class Metrics {

    @Value("${metrics.server.host}")
    private String host;
    
    @Value("${metrics.enable}")
    private Boolean enableMetrics;
    
    @Value("${metrics.server.port}")
    private int port;

    @Bean
    public StatsDClient statsDClient() {
        if (enableMetrics==true){
            return new NonBlockingStatsDClient("csye6225", host, port);
        }
        else {
        return new NoOpStatsDClient();
        }
    }
}
