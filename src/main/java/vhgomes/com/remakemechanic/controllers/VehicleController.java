package vhgomes.com.remakemechanic.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vhgomes.com.remakemechanic.dtos.CreateVehicleDTO;
import vhgomes.com.remakemechanic.services.VehicleService;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping()
    public ResponseEntity<Void> createVehicle(@RequestBody CreateVehicleDTO createVehicleDTO,  JwtAuthenticationToken jwtAuthenticationToken) {
        return vehicleService.createVehicle(createVehicleDTO, jwtAuthenticationToken);
    }

}
