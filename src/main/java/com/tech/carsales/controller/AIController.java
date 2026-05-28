package com.tech.carsales.controller;

import com.tech.carsales.service.AIQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIQueryService aiQueryService;

    @PostMapping("/ask")
    ResponseEntity<String>ask(@RequestBody String question){
        return ResponseEntity.ok(aiQueryService.process(question));
    }
}
