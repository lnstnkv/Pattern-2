package ru.tsu.hits.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tsu.hits.creditservice.entity.PaymentEntity;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    @Query("select p from PaymentEntity p where p.userId = ?1")
    List<PaymentEntity> getAll(Integer userId);
}
