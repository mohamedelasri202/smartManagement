package com.logistics.Smangement.dto.user;



import com.logistics.Smangement.enums.Speciality;
import com.logistics.Smangement.enums.TransporteurStatus;
import com.logistics.Smangement.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String id;
    private String login;
    private UserRole role;
    private boolean active;


    private TransporteurStatus status;
    private Speciality speciality;
}