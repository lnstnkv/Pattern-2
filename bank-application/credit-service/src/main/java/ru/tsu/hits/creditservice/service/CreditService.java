package ru.tsu.hits.creditservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tsu.hits.creditservice.dto.CreateUpdateCreditRequest;
import ru.tsu.hits.creditservice.dto.PaymentRequest;
import ru.tsu.hits.creditservice.entity.CreditEntity;
import ru.tsu.hits.creditservice.entity.TariffEntity;
import ru.tsu.hits.creditservice.repository.CreditRepository;
import ru.tsu.hits.creditservice.repository.TariffRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreditService {

    private final TariffRepository tariffRepository;
    private final CreditRepository repository;

    @Autowired
    public CreditService(
            CreditRepository repository,
            TariffRepository tariffRepository
    ) {
        this.repository = repository;
        this.tariffRepository = tariffRepository;
    }

    public List<CreditEntity> get() {
        return repository.findAll();
    }

    public CreditEntity createCredit(CreateUpdateCreditRequest request) {
        TariffEntity tariff = tariffRepository.findByName(request.getTariffName()).orElseThrow();
        CreditEntity entity = map(request);
        entity.setDebt(entity.getCreditAmount() + entity.getCreditAmount() * (tariff.getPercentage() / 100));
        entity.setPayed(0F);
        entity.setCreditStart(Timestamp.valueOf(LocalDateTime.now()));

        entity = repository.save(entity);
        return entity;
    }

    public CreditEntity payDebt(PaymentRequest request) {
        CreditEntity credit = repository.findById(request.getCreditId()).orElseThrow();
        if (credit.getDebt() - request.getPayment() < 0) {
            credit.setPayed(credit.getPayed() + credit.getDebt());
            credit.setDebt(0F);
        } else {
            credit.setPayed(credit.getPayed() + request.getPayment());
            credit.setDebt(credit.getDebt() - request.getPayment());
        }
        repository.flush();
        return credit;
    }

    public List<CreditEntity> getByTariff(String tariffName) {
        return repository.findByTariff(tariffName);
    }

    public void payAllDebts() {
        List<CreditEntity> allCredits = repository.findAllDebtors();
        for (CreditEntity credit : allCredits) {
            if (credit.getDebt() - (credit.getCreditAmount() * 0.05F) < 0) {
                credit.setPayed(credit.getPayed() + credit.getDebt());
                credit.setDebt(0F);
            } else {
                credit.setPayed(credit.getPayed() + (credit.getCreditAmount() * 0.05F));
                credit.setDebt(credit.getDebt() - (credit.getCreditAmount() * 0.05F));
            }
            repository.save(credit);
        }
    }

    public CreditEntity map(CreateUpdateCreditRequest request) {
        CreditEntity entity = new CreditEntity();
        entity.setCreditDuration(request.getCreditDuration());
        entity.setCreditAmount(request.getCreditAmount());
        entity.setTariff(request.getTariffName());
        entity.setUserId(request.getUserId());
        return entity;
    }
}
