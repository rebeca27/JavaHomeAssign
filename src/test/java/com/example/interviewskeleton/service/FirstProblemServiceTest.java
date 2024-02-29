package com.example.interviewskeleton.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FirstProblemServiceTest {

    private FirstProblemService service;

    @BeforeEach
    public void setUp() {
        service = new FirstProblemService();
    }

    @Test
    void testSaveWordsPerformance() throws ExecutionException, InterruptedException { // Declare that this method can throw Exception
        List<String> words = List.of("test", "words", "without", "letter", "A");

        long startTime = System.currentTimeMillis();
        service.saveWords(words);
        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);

        assertTrue(endTime - startTime < 5000, "Saving 5 words took more than 500ms.");
    }

    @Test
    void testSaveWordsValidationFailure() {
        List<String> words = List.of("test", "worda", "without", "letter", "A");

        assertThrows(UnsupportedOperationException.class, () -> service.saveWords(words), "Expected to throw for invalid word.");
    }
}
