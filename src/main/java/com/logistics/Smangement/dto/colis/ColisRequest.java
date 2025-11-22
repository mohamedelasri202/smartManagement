package com.logistics.Smangement.dto.colis;

import com.logistics.Smangement.enums.ColisType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ColisRequest {

    @NotNull(message = "Type is required (STANDARD, FRAGILE, FRIGO)")
    private ColisType type;

    @Min(value = 0, message = "Weight must be positive")
    private double poids;

    @NotBlank(message = "Destination address is required")
    private String adresseDestination;


    private String instructionsManutention;


    private Double temperatureMin;
    private Double temperatureMax;
}