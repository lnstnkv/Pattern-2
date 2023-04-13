package ru.tsu.hits.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tsu.hits.creditservice.entity.CreditRatingEntity;

public interface CreditRatingRepository extends JpaRepository<CreditRatingEntity, Integer> {

    @Query("select r from CreditRatingEntity r where r.userId = ?1")
    CreditRatingEntity get(String userId);
}
