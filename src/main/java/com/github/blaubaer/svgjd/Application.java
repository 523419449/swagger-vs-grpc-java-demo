package com.github.blaubaer.svgjd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.springframework.boot.Banner.Mode.OFF;

@SpringBootApplication
@EnableSwagger2
public class Application {

    public static ConfigurableApplicationContext run(String... args) {
        final SpringApplication application = new SpringApplication(Application.class);
        application.setBannerMode(OFF);
        application.setLogStartupInfo(false);
        //noinspection resource
        return application.run(args);
    }

}