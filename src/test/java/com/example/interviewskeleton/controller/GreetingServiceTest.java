package com.example.interviewskeleton.controller;

import com.example.interviewskeleton.service.GreetingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingService greetingService;

    private Clock fixedClock;
    @TestConfiguration
    static class TestConfig {
        @Bean
        public Clock testClock() {
            return Clock.fixed(Instant.parse("2024-02-29T09:00:00Z"), ZoneId.of("UTC"));
        }
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void greetingAtMorningInEnglish() throws Exception {
        when(greetingService.getGreetingMessage("John", Locale.ENGLISH))
                .thenReturn("Good morning, John!");

        mockMvc.perform(get("/greet/John?locale=en")
                        .header("X-Auth-Token", "such-secure-much-wow")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Good morning, John!"));
    }


    @Test
    void greetingAtAfternoonInSpanish() throws Exception {
        fixedClock = Clock.fixed(Instant.parse("2024-02-29T15:00:00Z"), ZoneId.of("UTC"));

        when(greetingService.getGreetingMessage("Juan", new Locale("es")))
                .thenReturn("¡Buenas tardes, Juan!");

        mockMvc.perform(get("/greet/Juan?locale=es")
                        .header("X-Auth-Token", "such-secure-much-wow")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Â¡Buenas tardes, Juan!"));
    }
}

