package com.signify.reviews.Alexa_Reviews.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {

//    @Bean
//    public WebMvcConfigurer corsConfigurer(){
//        return new WebMvcConfigurer() {
//            @Override
//            public  void addCorsMappings(CorsRegistry registry){
//                registry.addMapping("/review/**")
//                        .allowedMethods("GET","POST")
//                        .allowedOrigins("*");
//            }
//        };
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/review/**") // Replace with your API endpoint path
                .allowedOrigins("*") // Replace with the domain of the 3rd party browser application
                .allowedMethods("GET", "POST"); // Specify the allowed HTTP methods
    }
}
