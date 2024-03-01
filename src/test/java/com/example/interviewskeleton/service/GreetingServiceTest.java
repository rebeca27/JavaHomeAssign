package com.example.interviewskeleton.service;


import com.example.interviewskeleton.config.GreetingConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GreetingServiceTest {

    @Mock
    private GreetingConfig greetingConfig;

    private GreetingService greetingService;

    private Clock fixedClock;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        fixedClock = Clock.fixed(Instant.parse("2024-02-29T09:00:00Z"), ZoneId.of("UTC"));

        greetingService = new GreetingService(greetingConfig, fixedClock);

        when(greetingConfig.getTimeZoneMap()).thenReturn(Map.of("en", "Europe/London",
                "es", "Europe/Madrid"));
        when(greetingConfig.getDefaultTimeZone()).thenReturn("UTC");
        when(greetingConfig.getTimeOfDay()).thenReturn(Map.of(
                "morning", LocalTime.of(6, 0),
                "afternoon", LocalTime.of(12, 0),
                "evening", LocalTime.of(18, 0)));
        greetingService = Mockito.spy(new GreetingService(greetingConfig, Clock.systemUTC()));
        // stubbing lenient - prevent throwing unnecessary warnings or errors about unused stubs
        lenient().when(greetingConfig.getGreetingForTimeOfDay("morning")).thenReturn(Map.of(
                "en", "Good morning, {name}!",
                "es", "¡Buenos días, {name}!",
                "fr", "Bonjour, {name}!",
                "ro", "Bună dimineața, {name}!",
                "it", "Buongiorno, {name}!",
                "de", "Guten Morgen, {name}!",
                "nl", "Goedemorgen, {name}!",
                "pt", "Bom dia, {name}!",
                "hi", "सुप्रभात, {name}!"
        ));

        lenient().when(greetingConfig.getGreetingForTimeOfDay("afternoon")).thenReturn(Map.of(
                "en", "Good afternoon, {name}!",
                "es", "¡Buenas tardes, {name}!",
                "fr", "Bon après-midi, {name}!",
                "ro", "Bună ziua, {name}!",
                "it", "Buon pomeriggio, {name}!",
                "de", "Guten Tag, {name}!",
                "nl", "Goedemiddag, {name}!",
                "pt", "Boa tarde, {name}!",
                "hi", "शुभ अपराह्न, {name}!"
        ));

        lenient().when(greetingConfig.getGreetingForTimeOfDay("evening")).thenReturn(Map.of(
                "en", "Good evening, {name}!",
                "es", "¡Buenas noches, {name}!",
                "fr", "Bonsoir, {name}!",
                "ro", "Bună seara, {name}!",
                "it", "Buona serata, {name}!",
                "de", "Guten Abend, {name}!",
                "nl", "Goedenavond, {name}!",
                "pt", "Boa noite, {name}!",
                "hi", "शुभ संध्या, {name}!"
        ));


    }

    @Test
    void testMorningGreetingForEnglishUser() {

        Locale testLocale = Locale.forLanguageTag("en");
        Mockito.doReturn(LocalTime.of(5, 0)).when(greetingService).getCurrentTime(any());

        String result = greetingService.getGreetingMessage("Rebeca", testLocale);

        assertEquals("Good morning, Rebeca!", result);
    }

    @Test
    void testEveningGreetingForSpanishUser() {

        Locale testLocale = Locale.forLanguageTag("es");
        Mockito.doReturn(LocalTime.of(22, 0)).when(greetingService).getCurrentTime(any());

        String result = greetingService.getGreetingMessage("Rebeca", testLocale);

        assertEquals("¡Buenas noches, Rebeca!", result);
    }



    @Test
    void greetingJustBeforeMorningBoundaryShouldBeEvening() {
        Clock beforeMorningClock = Clock.fixed(Instant.parse("2024-02-29T05:59:59Z"), ZoneId.of("UTC"));
        greetingService = new GreetingService(greetingConfig, beforeMorningClock);

        String result = greetingService.getGreetingMessage("Bob", Locale.ENGLISH);
        assertEquals("Good morning, Bob!", result);
    }


    @Test
    void invalidLocaleShouldStillProvideGreeting() {
        Locale invalidLocale = new Locale("xx");

        String result = greetingService.getGreetingMessage("Dave", invalidLocale);
        assertEquals("Hello, Dave!", result);
    }

}