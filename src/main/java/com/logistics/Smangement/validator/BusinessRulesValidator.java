package com.logistics.Smangement.validator;



import com.logistics.Smangement.entity.Colis;
import com.logistics.Smangement.entity.User;
import com.logistics.Smangement.enums.ColisType;
import com.logistics.Smangement.enums.Speciality;
import com.logistics.Smangement.enums.UserRole;
import com.logistics.Smangement.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class BusinessRulesValidator {


    public void validateAssignment(Colis colis, User transporteur) {

        if (transporteur.getRole() != UserRole.TRANSPORTEUR) {
            throw new BusinessException("User " + transporteur.getLogin() + " is not a TRANSPORTEUR.");
        }


        if (!transporteur.isActive()) {
            throw new BusinessException("Transporteur " + transporteur.getLogin() + " is currently inactive.");
        }


        if (!isCompatible(colis.getType(), transporteur.getSpeciality())) {
            throw new BusinessException(String.format(
                    "Incompatible Assignment: Colis is %s but Transporteur speciality is %s",
                    colis.getType(), transporteur.getSpeciality()
            ));
        }
    }

    private boolean isCompatible(ColisType colisType, Speciality speciality) {

        if (colisType == null || speciality == null) return false;
        return colisType.name().equals(speciality.name());
    }
}
