package com.example.interviewskeleton.controller;

import com.example.interviewskeleton.dto.FirstProblemRequest;
import com.example.interviewskeleton.service.FirstProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;


@RestController
@RequiredArgsConstructor
public class FirstProblemController {
    private final FirstProblemService firstProblemService;

    @PostMapping("/first/words/save")
    public ResponseEntity<String> saveWords(@RequestBody FirstProblemRequest firstProblemRequest)throws ExecutionException, InterruptedException {
        try {
            firstProblemService.saveWords(firstProblemRequest.getWords());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.CREATED).body(e.getMessage());
        }
    }

}
