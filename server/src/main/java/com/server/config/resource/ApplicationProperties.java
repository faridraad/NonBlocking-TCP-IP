package com.server.config.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import javax.annotation.Resource;

@Configuration
@PropertySources(
        {
        @PropertySource(name = "application",   value = "classpath:/application.yml", encoding = "UTF-8", ignoreResourceNotFound = true)
        }
)
public class ApplicationProperties {
    @Resource
    private Environment environment;
    public String getProperty(String name) {
        return environment.getProperty(name).trim();
    }
}
