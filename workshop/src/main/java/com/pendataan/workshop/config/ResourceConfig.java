package com.pendataan.workshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/himatika/proposal-workshop/input/upload/**").addResourceLocations("file:files/proposal-workshop");
        registry.addResourceHandler("/himatika/lpj-workshop/input/upload/**").addResourceLocations("file:files/lpj-workshop");
    }
}
