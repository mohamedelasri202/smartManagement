package com.logistics.Smangement.dto.user;



import com.logistics.Smangement.enums.Speciality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import  com.logistics.Smangement.enums.TransporteurStatus;

@Data

public class TransporteurRequest {
//    @NotBlank(message = "Login is required")
    private String login;

//    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Speciality is required")
    private Speciality speciality;

    private Boolean active;


    private TransporteurStatus status;
}