package com.logistics.Smangement.controller;


import com.logistics.Smangement.dto.user.UserResponse;
import com.logistics.Smangement.dto.user.TransporteurRequest;

import com.logistics.Smangement.enums.Speciality;
import com.logistics.Smangement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin - User Management", description = "Manage carriers and users")
public class AdminUserController {

    private final UserService userService;



    @GetMapping("/users")
    @Operation(summary = "List all users (Admin & Transporteurs)")
    public ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PatchMapping("/users/{id}/reactivate")
    @Operation(summary = "Reactivate a disabled user account")
    public ResponseEntity<String> reactivateUser(@PathVariable String id) {
        userService.reactivateUser(id);
        return ResponseEntity.ok("the transporteur has been reactivated");
    }



    @GetMapping("/transporteurs")
    @Operation(summary = "List transporteurs with optional speciality filter")
    public ResponseEntity<Page<UserResponse>> getTransporteurs(
            @RequestParam(required = false) Speciality speciality,
            Pageable pageable) {
        return ResponseEntity.ok(userService.getTransporteurs(speciality, pageable));
    }

    @PostMapping("/transporteurs")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new Transporteur")
    public ResponseEntity<UserResponse> createTransporteur(@Valid @RequestBody TransporteurRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createTransporteur(request));
    }

    @PutMapping("/transporteurs/{id}")
    @Operation(summary = "Update an existing Transporteur")
    public ResponseEntity<UserResponse> updateTransporteur(
            @PathVariable String id,
            @Valid @RequestBody TransporteurRequest request) {
        return ResponseEntity.ok(userService.updateTransporteur(id, request));
    }

    @DeleteMapping("/transporteurs/{id}")
    @Operation(summary = "Deactivate (delete) a Transporteur")
    public ResponseEntity<String> deleteTransporteur(@PathVariable String id) {
        userService.deleteTransporteur(id);
        return ResponseEntity.ok("THE transporteur has been deleted");
    }
}