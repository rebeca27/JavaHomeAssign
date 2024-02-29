package com.example.interviewskeleton.controller;

import com.example.interviewskeleton.service.GreetingService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;


@RestController
public class GreetingController {

    private final GreetingService greetingService;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
    @GetMapping("/greet/{name}")
    public ResponseEntity<String> getGreetingMessage(@NonNull @PathVariable("name") String userName,
                                                     @NonNull @RequestParam String locale) {
        return ResponseEntity.ok(greetingService.getGreetingMessage(userName, Locale.forLanguageTag(locale)));
    }
}


