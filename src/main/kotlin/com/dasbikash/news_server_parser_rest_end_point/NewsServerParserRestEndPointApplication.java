package com.dasbikash.news_server_parser_rest_end_point;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources(
        {
                @PropertySource("classpath:application.properties"),
                @PropertySource("classpath:spring_mvc_rest.properties")
        }
    )
public class NewsServerParserRestEndPointApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(NewsServerParserRestEndPointApplication.class, args);
    }
}
