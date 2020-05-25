package com.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author tycoding
 * @date 2019-06-10
 */
@Configuration
public class WebsocketAutoConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public MyEndpointConfigure newConfigure() {
        return new MyEndpointConfigure();
    }
}
