package ru.tsu.hits.creditservice.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CreditService {

    private final TariffRepository tariffRepository;
    private final CreditRepository repository;
    private final PaymentService paymentService;
    private final CreditRatingService ratingService;

    public List<CreditEntity> get() {
        return repository.findAll();
    }

    public List<CreditEntity> getByUser(Integer userId){
        return repository.findByUserId(userId);
    }

    public CreditEntity createCredit(CreateUpdateCreditRequest request) {
        TariffEntity tariff = tariffRepository.findByName(request.getTariffName()).orElseThrow();
        CreditEntity entity = map(request);
        entity.setDebt(entity.getCreditAmount() + entity.getCreditAmount() * (tariff.getPercentage() / 100));
        entity.setPayed(0F);
        entity.setCreditStart(Timestamp.valueOf(LocalDateTime.now()));
        entity.setClosed(false);
        entity = repository.save(entity);
        ratingService.createRating(entity.getUserId());
        return entity;
    }

    public CreditEntity payDebt(PaymentRequest request) {
        CreditEntity credit = repository.findById(request.getCreditId()).orElseThrow();
        float payed;
        float paymentValue;
        if (credit.getDebt() - request.getPayment() < 0) {
            payed = credit.getPayed() + credit.getDebt();
            paymentValue = credit.getDebt();
            credit.setDebt(0F);
            credit.setClosed(true);
            ratingService.updateRating(credit.getUserId());
        } else {
            payed = credit.getPayed() + request.getPayment();
            paymentValue = request.getPayment();
            credit.setDebt(credit.getDebt() - request.getPayment());
        }
        credit.setPayed(payed);
        paymentService.createPayment(credit.getUserId(), paymentValue);
        repository.flush();
        return credit;
    }

    public List<CreditEntity> getByTariff(String tariffName) {
        return repository.findByTariff(tariffName);
    }

    public void payAllDebts() {
        List<CreditEntity> allCredits = repository.findAllDebtors();
        for (CreditEntity credit : allCredits) {
            float payed;
            float paymentValue;
            if (credit.getDebt() - (credit.getCreditAmount() * 0.05F) < 0) {
                payed = credit.getPayed() + credit.getDebt();
                paymentValue = credit.getDebt();
                credit.setDebt(0F);
                credit.setClosed(true);
                ratingService.updateRating(credit.getUserId());
            } else {
                payed = credit.getPayed() + (credit.getCreditAmount() * 0.05F);
                paymentValue = credit.getCreditAmount() * 0.05F;
                credit.setDebt(credit.getDebt() - (credit.getCreditAmount() * 0.05F));
            }
            credit.setPayed(payed);
            paymentService.createPayment(credit.getUserId(), paymentValue);
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
