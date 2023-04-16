package ru.tsu.hits.creditservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tsu.hits.creditservice.dto.AmountOfMoney;
import ru.tsu.hits.creditservice.dto.Account;
import ru.tsu.hits.creditservice.dto.CreateUpdateCreditRequest;
import ru.tsu.hits.creditservice.dto.PaymentRequest;
import ru.tsu.hits.creditservice.dto.UserAccount;
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
    private final WebClient webClient;

    public List<CreditEntity> get() {
        return repository.findAll();
    }

    public List<CreditEntity> getByUser(String userId) {
        return repository.findByUserId(userId);
    }

    public CreditEntity createCredit(CreateUpdateCreditRequest request) {
        TariffEntity tariff = tariffRepository.findByName(request.getTariffName()).orElseThrow();
        UserAccount account = webClient.get()
                .uri(String.format("http://host.docker.internal:8000/users/%s/accounts", request.getUserId()))
                .retrieve()
                .bodyToMono(UserAccount.class)
                .block();
        if (account == null) {
            throw new IllegalArgumentException("Something went wrong with CORE service");
        }
        if (account.getTotalCount() == 0) {
            throw new IllegalArgumentException("There's no client with such id ID");
        }
        CreditEntity existing = repository.findByAccountId(request.getAccountId());
        if (existing != null && !existing.isClosed()) {
            throw new IllegalArgumentException("For this client already exists not payed credit");
        }
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
        Account account = webClient.get()
                .uri(String.format("http://host.docker.internal:8000/accounts/%s", request.getAccountId()))
                .retrieve()
                .bodyToMono(Account.class)
                .block();
        if (account == null) {
            throw new IllegalArgumentException("Something went wrong with CORE service");
        }
        if (request.getAmount() > account.getBalance()) {
            throw new IllegalArgumentException("Payment sum more then balance in core");
        }
        float payed;
        float paymentValue;
        if (credit.getDebt() - request.getAmount() < 0) {
            payed = credit.getPayed() + credit.getDebt();
            paymentValue = credit.getDebt();
            credit.setDebt(0F);
            credit.setClosed(true);
            ratingService.updateRating(credit.getUserId());
        } else {
            payed = credit.getPayed() + request.getAmount();
            paymentValue = request.getAmount();
            credit.setDebt(credit.getDebt() - request.getAmount());
        }
        credit.setPayed(payed);
        paymentService.createPayment(credit.getAccountId(), paymentValue);
        payDebt(request.getAccountId(), paymentValue);
        repository.flush();
        return credit;
    }

    public List<CreditEntity> getByTariff(String tariffName) {
        return repository.findByTariff(tariffName);
    }

    public void payAllDebts() {
        List<CreditEntity> allCredits = repository.findAllDebtors();
        for (CreditEntity credit : allCredits) {
            Account account = webClient.get()
                    .uri(String.format("http://host.docker.internal:8000/accounts/%s", credit.getAccountId()))
                    .retrieve()
                    .bodyToMono(Account.class)
                    .block();
            if (account == null) {
                throw new IllegalArgumentException("Something went wrong with CORE service");
            }
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
            if (paymentValue > account.getBalance()) {
                paymentService.createPayment(credit.getAccountId(), 0F);
            } else {
                credit.setPayed(payed);
                paymentService.createPayment(credit.getAccountId(), paymentValue);
                payDebt(credit.getAccountId(), paymentValue);
                repository.save(credit);
            }
        }
    }

    public CreditEntity map(CreateUpdateCreditRequest request) {
        CreditEntity entity = new CreditEntity();
        entity.setCreditDuration(request.getCreditDuration());
        entity.setCreditAmount(request.getCreditAmount());
        entity.setTariff(request.getTariffName());
        entity.setUserId(request.getUserId());
        entity.setAccountId(request.getAccountId());
        return entity;
    }

    private void payDebt(String accountId, Float amount) {
        webClient.post()
                .uri(String.format("http://host.docker.internal:8001/operations/%s/withdraw", accountId))
                .bodyValue(new AmountOfMoney(amount))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
