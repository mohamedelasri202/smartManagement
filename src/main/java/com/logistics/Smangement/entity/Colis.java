package com.logistics.Smangement.entity;



import com.logistics.Smangement.enums.ColisStatus;
import com.logistics.Smangement.enums.ColisType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "colis")
public abstract class Colis {

    @Id
    private String id;

    private double poids;
    private String adresseDestination;
    private ColisStatus statut;


    private ColisType type;


    @DBRef
    private User transporteur;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}