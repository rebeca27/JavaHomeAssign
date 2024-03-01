package com.example.interviewskeleton.service;


import com.example.interviewskeleton.config.GreetingConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;


@Service
@Slf4j
@AllArgsConstructor
public class GreetingService {
    private final GreetingConfig greetingConfig;
    private final Clock clock;

    public String getGreetingMessage(String userName, Locale locale) {
        String timeOfDay = determineTimeOfDay(locale);
        String language = locale.getLanguage();
        Map<String, String> greetingsForTimeOfDay = greetingConfig.getGreetingForTimeOfDay(timeOfDay);

        String greetingTemplate = greetingsForTimeOfDay.getOrDefault(language, "Hello, {name}!"); // Fallback default greeting
        return greetingTemplate.replace("{name}", userName);
    }

    private String determineTimeOfDay(Locale locale) {
        ZoneId zoneId = getZoneId(locale);
        LocalTime currentTime = getCurrentTime(zoneId);

        Map<String, LocalTime> timesOfDay = greetingConfig.getTimeOfDay();
        if (currentTime.isBefore(timesOfDay.get("morning"))) {
            return "morning";
        } else if (currentTime.isBefore(timesOfDay.get("afternoon"))) {
            return "afternoon";
        } else {
            return "evening";
        }
    }

    public GreetingConfig getGreetingConfig() {
        return greetingConfig;
    }

    public Clock getClock() {
        return clock;
    }

    protected LocalTime getCurrentTime(ZoneId zoneId) {
        return LocalTime.now(clock.withZone(zoneId));
    }
    private ZoneId getZoneId(Locale locale) {
        String defaultZoneId = greetingConfig.getDefaultTimeZone();
        ZoneId zoneId;
        try {
            String zoneIdStr = greetingConfig.getTimeZoneMap().getOrDefault(locale.getLanguage(), defaultZoneId);
            zoneId = ZoneId.of(zoneIdStr);
        } catch (DateTimeException e) {
            log.error("Invalid timezone '{}': {}. Will use default timezone: {}", locale, e.getMessage(), defaultZoneId);
            zoneId = ZoneId.of(defaultZoneId);
        }
        return zoneId;
    }

}

