package vhgomes.com.remakemechanic.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vhgomes.com.remakemechanic.dtos.CreateVehicleDTO;
import vhgomes.com.remakemechanic.models.Vehicle;
import vhgomes.com.remakemechanic.repositories.UserRepository;
import vhgomes.com.remakemechanic.repositories.VehicleRepository;

import java.util.UUID;

@Service
public class VehicleService {
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public VehicleService(UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public ResponseEntity<Void> createVehicle(CreateVehicleDTO createVehicleDTO, JwtAuthenticationToken jwtAuthenticationToken) {
        var client = userRepository.findById(UUID.fromString(jwtAuthenticationToken.getName())).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY));

        var createdVehicle = new Vehicle();
        createdVehicle.setPlaca(createVehicleDTO.placa());
        createdVehicle.setBrand(createVehicleDTO.brand());
        createdVehicle.setClient(client);
        createdVehicle.setCarYear(createVehicleDTO.carYear());
        createdVehicle.setModel(createVehicleDTO.model());

        vehicleRepository.save(createdVehicle);

        return ResponseEntity.ok().build();
    }
}
