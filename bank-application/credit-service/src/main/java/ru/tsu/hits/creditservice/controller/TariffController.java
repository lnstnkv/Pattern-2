package ru.tsu.hits.creditservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tsu.hits.creditservice.entity.TariffEntity;
import ru.tsu.hits.creditservice.dto.CreateUpdateTariffRequest;
import ru.tsu.hits.creditservice.service.TariffService;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
@CrossOrigin
public class TariffController {

    private final TariffService service;

    @Autowired
    public TariffController(TariffService service) {
        this.service = service;
    }

    @GetMapping
    public List<TariffEntity> get() {
        return service.get();
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public TariffEntity createTariff(@RequestBody CreateUpdateTariffRequest request) throws Exception {
        return service.createTariff(request);
    }
}
