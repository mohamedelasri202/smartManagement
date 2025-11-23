package com.logistics.Smangement.mapper;

import com.logistics.Smangement.dto.user.UserResponse; // Make sure this import matches your project structure
import com.logistics.Smangement.dto.colis.ColisRequest;
import com.logistics.Smangement.dto.colis.ColisResponse;
import com.logistics.Smangement.entity.Colis;
import com.logistics.Smangement.entity.FragileColis;
import com.logistics.Smangement.entity.FrigoColis;
import com.logistics.Smangement.entity.StandardColis;
import com.logistics.Smangement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ColisMapper {


    default ColisResponse toResponse(Colis colis) {
        if (colis == null) return null;

        ColisResponse.ColisResponseBuilder response = ColisResponse.builder();

        response.id(colis.getId());
        response.type(colis.getType());
        response.poids(colis.getPoids());
        response.adresseDestination(colis.getAdresseDestination());
        response.statut(colis.getStatut());
        response.createdDate(colis.getCreatedDate());
        response.lastModifiedDate(colis.getLastModifiedDate());


        if (colis.getTransporteur() != null) {
            User driverEntity = colis.getTransporteur();


            UserResponse driverDto = UserResponse.builder()
                    .id(driverEntity.getId())
                    .login(driverEntity.getLogin())
                    .role(driverEntity.getRole())
                    .speciality(driverEntity.getSpeciality())
                    .status(driverEntity.getStatus())
                    .active(driverEntity.isActive())
                    .build();

            response.transporteur(driverDto);
        }


        if (colis instanceof FrigoColis) {
            FrigoColis frigo = (FrigoColis) colis;
            response.temperatureMin(frigo.getTemperatureMin());
            response.temperatureMax(frigo.getTemperatureMax());
        }
        else if (colis instanceof FragileColis) {
            FragileColis fragile = (FragileColis) colis;
            response.instructionsManutention(fragile.getInstructionsManutention());
        }

        return response.build();
    }

    default Colis toEntity(ColisRequest request) {
        if (request == null || request.getType() == null) {
            return null;
        }

        switch (request.getType()) {
            case FRAGILE:
                return FragileColis.builder()
                        .type(request.getType())
                        .poids(request.getPoids())
                        .adresseDestination(request.getAdresseDestination())
                        .instructionsManutention(request.getInstructionsManutention())
                        .build();
            case FRIGO:
                return FrigoColis.builder()
                        .type(request.getType())
                        .poids(request.getPoids())
                        .adresseDestination(request.getAdresseDestination())
                        .temperatureMin(request.getTemperatureMin())
                        .temperatureMax(request.getTemperatureMax())
                        .build();
            case STANDARD:
            default:
                return StandardColis.builder()
                        .type(request.getType())
                        .poids(request.getPoids())
                        .adresseDestination(request.getAdresseDestination())
                        .build();
        }
    }
}