package com.example.interviewskeleton;

import com.example.interviewskeleton.config.GreetingConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class InterviewSkeletonApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewSkeletonApplication.class, args);
    }

}
