package com.cv.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.cv.engine.CvReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
    public CvReader getCVReader() {
        return new CvReader();
    }

    @Bean
    public Gson getGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
