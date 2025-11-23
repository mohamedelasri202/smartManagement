package com.logistics.Smangement.mapper;

import com.logistics.Smangement.dto.user.TransporteurRequest;
import com.logistics.Smangement.dto.user.UserResponse;
import com.logistics.Smangement.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse toResponse(User user);


    @Mapping(target = "role", constant = "TRANSPORTEUR")
//    @Mapping(target = "active", constant = "true")
//    @Mapping(target = "status", constant = "DISPONIBLE")
    User toTransporteurEntity(TransporteurRequest request);
}