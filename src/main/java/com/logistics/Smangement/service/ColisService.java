package com.logistics.Smangement.service;

import com.logistics.Smangement.dto.colis.ColisRequest;
import com.logistics.Smangement.dto.colis.ColisResponse;
import com.logistics.Smangement.entity.Colis;
import com.logistics.Smangement.entity.User;
import com.logistics.Smangement.enums.ColisStatus;
import com.logistics.Smangement.enums.ColisType;
import com.logistics.Smangement.enums.UserRole;
import com.logistics.Smangement.exception.BusinessException;
import com.logistics.Smangement.exception.ResourceNotFoundException;
import com.logistics.Smangement.mapper.ColisMapper;
import com.logistics.Smangement.repository.ColisRepository;
import com.logistics.Smangement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColisService {

    private final ColisRepository colisRepository;
    private final UserRepository userRepository;
    private final ColisMapper colisMapper;



    public Page<ColisResponse> getAllColis(ColisType type, ColisStatus status, String destination, Pageable pageable) {

        return colisRepository.findAll(pageable).map(colisMapper::toResponse);
    }



    public ColisResponse createColis(ColisRequest request) {
        Colis colis = colisMapper.toEntity(request);
        colis.setStatut(ColisStatus.EN_ATTENTE);
        return colisMapper.toResponse(colisRepository.save(colis));
    }



    public ColisResponse assignColis(String colisId, String transporteurId) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis not found"));

        User transporteur = userRepository.findById(transporteurId)
                .orElseThrow(() -> new ResourceNotFoundException("Transporteur not found"));


        if (transporteur.getRole() != UserRole.TRANSPORTEUR) {
            throw new BusinessException("User is not a transporteur");
        }


        if (!colis.getType().name().equals(transporteur.getSpeciality().name())) {
            throw new BusinessException("Speciality mismatch: Parcel is " + colis.getType()
                    + " but Carrier is " + transporteur.getSpeciality());
        }

        colis.setTransporteur(transporteur);
        colis.setStatut(ColisStatus.EN_TRANSIT); // Auto-update status
        return colisMapper.toResponse(colisRepository.save(colis));
    }



    public ColisResponse updateColis(String id, ColisRequest request) {
        Colis existing = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis not found"));

        existing.setPoids(request.getPoids());
        existing.setAdresseDestination(request.getAdresseDestination());



        return colisMapper.toResponse(colisRepository.save(existing));
    }

    public void deleteColis(String id) {
        if (!colisRepository.existsById(id)) {
            throw new ResourceNotFoundException("Colis not found");
        }
        colisRepository.deleteById(id);
    }




    public Page<ColisResponse> getColisByTransporteur(String login, ColisType type, ColisStatus status, String destination, Pageable pageable) {

        User driver = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Transporteur not found"));


        return colisRepository.findAllByTransporteur(driver, pageable)
                .map(colisMapper::toResponse);
    }

    public ColisResponse updateStatus(String id, ColisStatus newStatus, String transporteurLogin) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis not found"));

        if (colis.getTransporteur() == null || !colis.getTransporteur().getLogin().equals(transporteurLogin)) {
            throw new BusinessException("You are not assigned to this parcel");
        }

        colis.setStatut(newStatus);

        return colisMapper.toResponse(colisRepository.save(colis));
    }
}