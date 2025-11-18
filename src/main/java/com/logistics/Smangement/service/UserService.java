package com.logistics.Smangement.service;

import com.logistics.Smangement.dto.user.TransporteurRequest;
import com.logistics.Smangement.dto.user.UserResponse;
import com.logistics.Smangement.entity.User;
import com.logistics.Smangement.enums.Speciality;
import com.logistics.Smangement.enums.UserRole;
import com.logistics.Smangement.exception.ResourceNotFoundException;
import com.logistics.Smangement.mapper.UserMapper;
import com.logistics.Smangement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    public Page<UserResponse> getTransporteurs(Speciality speciality, Pageable pageable) {

        if (speciality != null) {
            return userRepository.findByRoleAndSpeciality(UserRole.TRANSPORTEUR, speciality, pageable)
                    .map(userMapper::toResponse);
        }
        return userRepository.findByRole(UserRole.TRANSPORTEUR, pageable)
                .map(userMapper::toResponse);
    }

    public UserResponse createTransporteur(TransporteurRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new IllegalArgumentException("Login already exists");
        }

        User transporteur = userMapper.toTransporteurEntity(request);

        transporteur.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toResponse(userRepository.save(transporteur));
    }

    public UserResponse updateTransporteur(String id, TransporteurRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporteur not found"));




        if (request.getLogin() != null && !request.getLogin().isBlank()) {
            user.setLogin(request.getLogin());
        }


        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }


        if (request.getSpeciality() != null) {
            user.setSpeciality(request.getSpeciality());
        }

        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }


        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }


        return userMapper.toResponse(userRepository.save(user));
    }

    public void deleteTransporteur(String id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }

    public void reactivateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(true);
        userRepository.save(user);
    }
}