package ru.tsu.hits.creditservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.tsu.hits.creditservice.dto.CreateUpdateTariffRequest;
import ru.tsu.hits.creditservice.entity.TariffEntity;
import ru.tsu.hits.creditservice.service.TariffService;

import java.util.List;

@Controller
public class TariffControllerWebSocket {

    private final TariffService service;

    @Autowired
    public TariffControllerWebSocket(TariffService service) {
        this.service = service;
    }

    @MessageMapping("/get-all-tariffs")
    @SendTo("/creditServiceTopic/get-all-tariffs")
    public List<TariffEntity> getAll() {
        return service.get();
    }

    @MessageMapping("/create-tariff")
    @SendTo("/creditServiceTopic/create-tariff")
    public TariffEntity createTariff(CreateUpdateTariffRequest request) throws Exception {
        return service.createTariff(request);
    }
}
