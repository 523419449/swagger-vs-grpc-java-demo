package com.github.blaubaer.svgjd.swagger;

import com.github.blaubaer.svgjd.swagger.client.DefaultApi;
import com.github.blaubaer.svgjd.swagger.client.handler.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloClientConfiguration {

    @Bean
    public DefaultApi defaultApi() {
        return new DefaultApi(new ApiClient()
                .setBasePath("http://localhost:8080")
        );
    }

}
