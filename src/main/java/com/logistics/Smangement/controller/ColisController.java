package com.logistics.Smangement.controller;



import com.logistics.Smangement.dto.colis.ColisRequest;
import com.logistics.Smangement.dto.colis.ColisResponse;
import com.logistics.Smangement.enums.ColisStatus;
import com.logistics.Smangement.enums.ColisType;
import com.logistics.Smangement.service.ColisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Colis Management", description = "Manage parcels (Admin & Transporteur views)")
public class ColisController {

    private final ColisService colisService;

//
//    @GetMapping("/admin/colis")
//    @PreAuthorize("hasRole('ADMIN')")
//    @Operation(summary = "Admin: List all colis with filters")
//    public ResponseEntity<Page<ColisResponse>> getAllColis(
//            @RequestParam(required = false) ColisType type,
//            @RequestParam(required = false) ColisStatus status,
//            @RequestParam(required = false) String destination,
//            Pageable pageable) {
//        return ResponseEntity.ok(colisService.getAllColis(type, status, destination, pageable));
//    }

    @PostMapping("/admin/colis")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Admin: Create a new colis (Standard, Fragile, or Frigo)")
    public ResponseEntity<ColisResponse> createColis(@Valid @RequestBody ColisRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(colisService.createColis(request));
    }

    @PutMapping("/admin/colis/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin: Modify an existing colis")
    public ResponseEntity<ColisResponse> updateColis(
            @PathVariable String id,
            @Valid @RequestBody ColisRequest request) {
        return ResponseEntity.ok(colisService.updateColis(id, request));
    }

        @DeleteMapping("/admin/colis/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Admin: Delete a colis")
    public ResponseEntity<String> deleteColis(@PathVariable String id) {
        colisService.deleteColis(id);
        return ResponseEntity.ok("THE COLIS HAS BEEN DELETED");
    }
//
//    @PutMapping("/admin/colis/{id}/assign/{transporteurId}")
//    @PreAuthorize("hasRole('ADMIN')")
//    @Operation(summary = "Admin: Assign a colis to a specific transporteur")
//    public ResponseEntity<ColisResponse> assignColis(
//            @PathVariable String id,
//            @PathVariable String transporteurId) {
//        return ResponseEntity.ok(colisService.assignColis(id, transporteurId));
//    }
//
//    @GetMapping("/transporteur/colis")
//    @PreAuthorize("hasRole('TRANSPORTEUR')")
//    @Operation(summary = "Transporteur: List MY assigned colis")
//    public ResponseEntity<Page<ColisResponse>> getMyColis(
//            @RequestParam(required = false) ColisType type,
//            @RequestParam(required = false) ColisStatus status,
//            @RequestParam(required = false) String destination,
//            Authentication authentication,
//
//            @PageableDefault(page = 0, size = 10) Pageable pageable) {
//
//        String currentUserId = authentication.getName();
//        return ResponseEntity.ok(colisService.getColisByTransporteur(currentUserId, type, status, destination, pageable));
//    }
//
//    @PatchMapping("/transporteur/colis/{id}/status")
//    @PreAuthorize("hasRole('TRANSPORTEUR')")
//    @Operation(summary = "Transporteur: Update status of MY assigned colis")
//    public ResponseEntity<ColisResponse> updateColisStatus(
//            @PathVariable String id,
//            @RequestParam ColisStatus newStatus,
//            Authentication authentication) {
//        String currentUserId = authentication.getName();
//        return ResponseEntity.ok(colisService.updateStatus(id, newStatus, currentUserId));
//    }
}