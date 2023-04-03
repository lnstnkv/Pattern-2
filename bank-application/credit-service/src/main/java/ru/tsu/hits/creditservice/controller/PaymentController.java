package ru.tsu.hits.creditservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsu.hits.creditservice.entity.PaymentEntity;
import ru.tsu.hits.creditservice.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @GetMapping("/{creditId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<PaymentEntity> getPayments(@PathVariable Integer creditId) {
        return service.getPayments(creditId);
    }
}
