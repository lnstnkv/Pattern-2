package ru.tsu.hits.creditservice.controller;

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
    public List<CreditEntity> get() {
        return service.get();
    }

    @GetMapping("/{tariff}")
    public List<CreditEntity> getAllWithTariff(@PathVariable String tariff) {
        return service.getByTariff(tariff);
    }

    @PostMapping
    public CreditEntity createEntity(@RequestBody CreateUpdateCreditRequest request) {
        return service.createCredit(request);
    }

    @PostMapping("/payment")
    public CreditEntity payDebt(@RequestBody PaymentRequest request) {
        return service.payDebt(request);
    }
}
