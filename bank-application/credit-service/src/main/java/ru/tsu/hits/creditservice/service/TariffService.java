package ru.tsu.hits.creditservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsu.hits.creditservice.dto.CreateUpdateTariffRequest;
import ru.tsu.hits.creditservice.entity.TariffEntity;
import ru.tsu.hits.creditservice.repository.TariffRepository;

import java.util.List;

@Service
public class TariffService {

    private final TariffRepository repository;

    @Autowired
    public TariffService(TariffRepository repository) {
        this.repository = repository;
    }

    public List<TariffEntity> get() {
        return repository.findAll();
    }

    public TariffEntity createTariff(CreateUpdateTariffRequest request) throws Exception {
        TariffEntity entity = repository.findByName(request.getName()).orElse(null);

        if (entity != null) {
            throw new Exception("There's already tariff with such name.");
        }

        entity = map(request);
        entity = repository.save(entity);
        return entity;
    }

    public TariffEntity map(CreateUpdateTariffRequest request) {
        TariffEntity entity = new TariffEntity();
        entity.setName(request.getName());
        entity.setPercentage(request.getPercentage());
        return entity;
    }
}
