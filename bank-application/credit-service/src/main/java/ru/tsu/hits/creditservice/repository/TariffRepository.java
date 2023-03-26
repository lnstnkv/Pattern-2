package ru.tsu.hits.creditservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tsu.hits.creditservice.entity.TariffEntity;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<TariffEntity, Integer> {

    Optional<TariffEntity> findByName(String name);
}
