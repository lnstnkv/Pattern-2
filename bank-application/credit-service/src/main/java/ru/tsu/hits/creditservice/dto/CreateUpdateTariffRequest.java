package ru.tsu.hits.creditservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUpdateTariffRequest {

    @NotBlank
    private String name;

    @NotBlank
    private float percentage;
}
