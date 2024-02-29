package com.example.interviewskeleton.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
public class FirstProblemService {
    private static final String FORBIDDEN_CHARACTER = "a";

    public void saveWords(List<String> words) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(words.size());
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        List<String> invalidWords = new ArrayList<>();

        for (String word : words) {
            if (word.contains(FORBIDDEN_CHARACTER)) {
                invalidWords.add(word);
            } else {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> saveWordToExternalApi(word), executor);
                futures.add(future);
            }
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
        executor.shutdown();

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
