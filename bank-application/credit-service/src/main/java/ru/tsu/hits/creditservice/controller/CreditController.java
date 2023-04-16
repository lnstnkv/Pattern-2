package ru.tsu.hits.creditservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.creditservice.dto.CreateUpdateCreditRequest;
import ru.tsu.hits.creditservice.dto.PaymentRequest;
import ru.tsu.hits.creditservice.entity.CreditEntity;
import ru.tsu.hits.creditservice.service.CreditService;

import java.util.List;

@RestController
@RequestMapping("/api/credits")
@CrossOrigin
public class CreditController {

    private final CreditService service;

    @Autowired
    public CreditController(CreditService service) {
        this.service = service;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public List<CreditEntity> get() {
        return service.get();
    }

    @GetMapping("/accounts/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<CreditEntity> getCreditByUser(@PathVariable String userId) {
        return service.getByUser(userId);
    }

    @GetMapping("/{tariffName}")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<CreditEntity> getAllWithTariff(@PathVariable String tariffName) {
        return service.getByTariff(tariffName);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public CreditEntity createEntity(@RequestBody CreateUpdateCreditRequest request) {
        return service.createCredit(request);
    }

    @PostMapping("/payment")
    @SecurityRequirement(name = "Bearer Authentication")
    public CreditEntity payDebt(@RequestBody PaymentRequest request) {
        return service.payDebt(request);
    }
}
