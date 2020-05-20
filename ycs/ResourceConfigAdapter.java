package io.junq.examples.boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class ResourceConfigAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/bookpic/**").addResourceLocations("file:/home/library/src/main/resources/static/bookpic/");
        registry.addResourceHandler("/barcodepic/**").addResourceLocations("file:/home/library/src/main/resources/static/barcodepic/");
        registry.addResourceHandler("/postpic/**").addResourceLocations("file:/home/library/src/main/resources/static/postpic/");

    }
}