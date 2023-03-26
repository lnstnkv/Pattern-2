package ru.tsu.hits.creditservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.tsu.hits.creditservice.dto.CreateUpdateCreditRequest;
import ru.tsu.hits.creditservice.dto.PaymentRequest;
import ru.tsu.hits.creditservice.dto.TariffNameDto;
import ru.tsu.hits.creditservice.entity.CreditEntity;
import ru.tsu.hits.creditservice.service.CreditService;

import java.util.List;

@Controller
public class CreditControllerWebSocket {

    private final CreditService service;

    @Autowired
    public CreditControllerWebSocket(CreditService service) {
        this.service = service;
    }

    @MessageMapping("/get-all-credits")
    @SendTo("/creditServiceTopic/get-all-credits")
    public List<CreditEntity> getAllCredits() {
        return service.get();
    }

    @MessageMapping("/get-credits-by-tariff")
    @SendTo("/creditServiceTopic/get-credits-by-tariff")
    public List<CreditEntity> getByTariff(TariffNameDto request) {
        return service.getByTariff(request.getTariffName());
    }

    @MessageMapping("/create-credit")
    @SendTo("/creditServiceTopic/create-credit")
    public CreditEntity createCredit(CreateUpdateCreditRequest request) {
        return service.createCredit(request);
    }

    @MessageMapping("/pay-debt")
    @SendTo("/creditServiceTopic/pay-debt")
    public CreditEntity payDebt(PaymentRequest request) {
        return service.payDebt(request);
    }
}
