package ru.tsu.hits.creditservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.creditservice.entity.PaymentEntity;
import ru.tsu.hits.creditservice.repository.PaymentRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    public List<PaymentEntity> getPayments(String accountId) {
        return repository.getAll(accountId);
    }

    public List<PaymentEntity> getNotPayedPayments(String accountId) {
        return repository.getNotPayedDebts(accountId);
    }

    public void createPayment(String accountId, Float payed) {
        PaymentEntity entity = new PaymentEntity();
        entity.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        entity.setAccountId(accountId);
        entity.setPayed(payed);
        repository.save(entity);
    }
}
