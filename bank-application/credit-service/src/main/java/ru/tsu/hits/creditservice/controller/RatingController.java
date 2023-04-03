package ru.tsu.hits.creditservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsu.hits.creditservice.entity.CreditRatingEntity;
import ru.tsu.hits.creditservice.service.CreditRatingService;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final CreditRatingService service;

    @GetMapping("/{userId}")
    public CreditRatingEntity getRating(@PathVariable Integer userId) {
        return service.getByUser(userId);
    }
}
