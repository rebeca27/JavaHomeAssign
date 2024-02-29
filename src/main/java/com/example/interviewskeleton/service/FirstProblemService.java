package com.example.interviewskeleton.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FirstProblemService {
    private static final String FORBIDDEN_CHARACTER = "a";

    public void saveWords(List<String> words) {
        List<String> invalidWords = new ArrayList<>();
        words.forEach(word -> {
            if (word.contains(FORBIDDEN_CHARACTER)) {
                invalidWords.add(word);
            } else {
                CompletableFuture.runAsync(() -> saveWordToExternalApi(word))
                        .exceptionally(throwable -> {
                            log.info("[BEST EFFORT] Saving word '{}' failed: {}", word, throwable.getMessage());
                            return null;
                        });
            }
        });

        if (!invalidWords.isEmpty()) {
            throw new UnsupportedOperationException("These words are not valid: " + invalidWords);
        }
    }




    private void saveWordToExternalApi(String word) {
        try {
            // Here we call external API. We use a sleep of 1s to simulate that it takes a lot of time, and we have no control over it.
            Thread.sleep(1000);
        } catch (Throwable anyException) {
            log.info("[BEST EFFORT] Saving word '{}' failed: {}", word, anyException.getMessage());
        }
    }
}
