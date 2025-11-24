package com.jafarov.quiz.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class VirtualThreadTomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatVirtualThreads() {
        return factory -> {
            Executor executor = Executors.newVirtualThreadPerTaskExecutor();
            factory.addConnectorCustomizers(connector -> {
                connector.getProtocolHandler().setExecutor(executor);
            });
        };
    }
}
