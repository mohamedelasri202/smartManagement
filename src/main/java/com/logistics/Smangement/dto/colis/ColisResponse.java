package com.logistics.Smangement.dto.colis;

import com.logistics.Smangement.dto.user.UserResponse;
import com.logistics.Smangement.enums.ColisStatus;
import com.logistics.Smangement.enums.ColisType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ColisResponse {
    private String id;
    private ColisType type;
    private double poids;
    private String adresseDestination;
    private ColisStatus statut;


    private UserResponse transporteur;


    private String instructionsManutention;
    private Double temperatureMin;
    private Double temperatureMax;

    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}