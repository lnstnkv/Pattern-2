package ru.tsu.hits.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tsu.hits.creditservice.entity.PaymentEntity;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    @Query("select p from PaymentEntity p where p.accountId = ?1")
    List<PaymentEntity> getAll(String accountId);

    @Query("select p from PaymentEntity p where p.accountId = ?1 and p.payed = 0")
    List<PaymentEntity> getNotPayedDebts(String accountId);
}
