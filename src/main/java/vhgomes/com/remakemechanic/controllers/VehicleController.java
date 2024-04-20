package vhgomes.com.remakemechanic.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import vhgomes.com.remakemechanic.dtos.CreateVehicleDTO;
import vhgomes.com.remakemechanic.dtos.EditVehicleDTO;
import vhgomes.com.remakemechanic.services.VehicleService;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping()
    public ResponseEntity<Void> createVehicle(@RequestBody CreateVehicleDTO createVehicleDTO, JwtAuthenticationToken jwtAuthenticationToken) {
        return vehicleService.createVehicle(createVehicleDTO, jwtAuthenticationToken);
    }

    @PostMapping("/{vehicleId}")
    public ResponseEntity<?> editVehicle(@RequestBody EditVehicleDTO editVehicleDTO, @PathVariable Long vehicleId, JwtAuthenticationToken jwtAuthenticationToken) {
        return vehicleService.editVehicleById(editVehicleDTO, jwtAuthenticationToken, vehicleId);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> getAllVehicles() {
        return vehicleService.getAllVehicle();
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<?> deleteVehicleById(@PathVariable Long vehicleId, JwtAuthenticationToken jwtAuthenticationToken) {
        return vehicleService.deleteVehicleById(vehicleId, jwtAuthenticationToken);
    }

}
