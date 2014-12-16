package com.boundmap.panner;


import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Import(ScreensConfiguration.class)
public class PlannerAppConfiguration {
    @Bean
    ApplicationUpdateUtils applicationUpdateUtils() {
        return new ApplicationUpdateUtils();
    }

    @Bean
    PropertiesFactoryBean projectProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("project.properties"));
        return propertiesFactoryBean;
    }

    @Bean
    AppConfig appConfig() throws DeserializationException {
        return AppConfigUtils.loadAppConfig();
    }

}

