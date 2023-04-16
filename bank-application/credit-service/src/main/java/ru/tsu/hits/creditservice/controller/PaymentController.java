package ru.tsu.hits.creditservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.creditservice.entity.PaymentEntity;
import ru.tsu.hits.creditservice.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin
public class PaymentController {

    private final PaymentService service;

    @GetMapping("/{accountId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<PaymentEntity> getPayments(@PathVariable String accountId) {
        return service.getPayments(accountId);
    }

    @GetMapping("/overdue/{accountId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<PaymentEntity> getNotPayed(@PathVariable String accountId) {
        return service.getNotPayedPayments(accountId);
    }
}
