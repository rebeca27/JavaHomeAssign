package com.example.interviewskeleton.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    //  /greet/John responds with 200 OK when authenticated
    @Test
    void shouldAllowRequestWithCorrectToken() throws Exception {
        mockMvc.perform(get("/greet/John?locale=en")
                        .header("X-Auth-Token", "such-secure-much-wow"))
                .andExpect(status().isOk());
    }

    // we expect unauthorized due to missing token
    @Test
    void shouldRejectRequestWithoutToken() throws Exception {
        mockMvc.perform(get("/greet/John?locale=en"))
                .andExpect(status().isUnauthorized());
    }

    // we expect unauthorized due to incorrect token
    @Test
    void shouldRejectRequestWithIncorrectToken() throws Exception {
        mockMvc.perform(get("/greet/John?locale=en")
                        .header("X-Auth-Token", "such-secure-much-wooow"))
                .andExpect(status().isUnauthorized());
    }
}
