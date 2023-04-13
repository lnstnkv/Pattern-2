package ru.tsu.hits.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tsu.hits.creditservice.entity.CreditEntity;

import java.util.List;

public interface CreditRepository extends JpaRepository<CreditEntity, Integer> {

    List<CreditEntity> findByTariff(String tariff);

    @Query("select c from CreditEntity c where c.debt > 0")
    List<CreditEntity> findAllDebtors();

    List<CreditEntity> findByUserId(String id);

    CreditEntity findByAccountId(String id);
}
