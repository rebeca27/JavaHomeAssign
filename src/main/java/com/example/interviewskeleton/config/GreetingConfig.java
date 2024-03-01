package com.example.interviewskeleton.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.Map;
import java.time.LocalTime;


@Configuration
@ConfigurationProperties(prefix = "greeting")
public class GreetingConfig {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
    private Map<String, String> morning;
    private Map<String, String> afternoon;
    private Map<String, String> evening;
    private Map<String, LocalTime> timeOfDay;
    private String defaultTimeZone;
    private Map<String, String> timeZoneMap;

    public Map<String, String> getGreetingForTimeOfDay(String timeOfDay) {
        switch (timeOfDay) {
            case "morning":
                return morning;
            case "afternoon":
                return afternoon;
            case "evening":
                return evening;
            default:
                throw new IllegalArgumentException("Invalid time of day");
        }
    }

    public Map<String, LocalTime> getTimeOfDay() {
        return timeOfDay;
    }

    public Map<String, String> getTimeZoneMap() {
        return timeZoneMap;
    }

    public String getDefaultTimeZone() {
        return defaultTimeZone;
    }

    public void setDefaultTimeZone(String defaultTimeZone) {
        this.defaultTimeZone = defaultTimeZone;
    }

    public void setTimeZoneMap(Map<String, String> timeZoneMap) {
        this.timeZoneMap = timeZoneMap;
    }

    public void setTimeOfDay(Map<String, LocalTime> timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public Map<String, String> getMorning() {
        return morning;
    }

    public void setMorning(Map<String, String> morning) {
        this.morning = morning;
    }

    public Map<String, String> getAfternoon() {
        return afternoon;
    }

    public void setAfternoon(Map<String, String> afternoon) {
        this.afternoon = afternoon;
    }

    public Map<String, String> getEvening() {
        return evening;
    }

    public void setEvening(Map<String, String> evening) {
        this.evening = evening;
    }
}
