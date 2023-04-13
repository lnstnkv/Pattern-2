package ru.tsu.hits.creditservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.creditservice.entity.CreditRatingEntity;
import ru.tsu.hits.creditservice.service.CreditRatingService;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
@CrossOrigin
public class RatingController {

    private final CreditRatingService service;

    @GetMapping("/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public CreditRatingEntity getRating(@PathVariable String userId) {
        return service.getByUser(userId);
    }
}
