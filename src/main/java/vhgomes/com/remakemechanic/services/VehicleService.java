package vhgomes.com.remakemechanic.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vhgomes.com.remakemechanic.dtos.ClientResponseDTO;
import vhgomes.com.remakemechanic.dtos.CreateVehicleDTO;
import vhgomes.com.remakemechanic.dtos.EditVehicleDTO;
import vhgomes.com.remakemechanic.dtos.VehicleReturnDTO;
import vhgomes.com.remakemechanic.models.Role;
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
        var client = userRepository.findById(UUID.fromString(jwtAuthenticationToken.getName())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var createdVehicle = new Vehicle();
        createdVehicle.setPlaca(createVehicleDTO.placa());
        createdVehicle.setBrand(createVehicleDTO.brand());
        createdVehicle.setClient(client);
        createdVehicle.setCarYear(createVehicleDTO.carYear());
        createdVehicle.setModel(createVehicleDTO.model());

        vehicleRepository.save(createdVehicle);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> editVehicleById(EditVehicleDTO editVehicleDTO, JwtAuthenticationToken jwtAuthenticationToken, Long vehicleId) {
        var client = userRepository.findById(UUID.fromString(jwtAuthenticationToken.getName())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (vehicle.getClient().getUserId() != client.getUserId()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        vehicle.setBrand(editVehicleDTO.brand());
        vehicle.setModel(editVehicleDTO.model());
        vehicle.setCarYear(editVehicleDTO.carYear());

        vehicleRepository.save(vehicle);

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getAllVehicle() {
        var vehicles = vehicleRepository.findAll().stream().map(vehicle -> new VehicleReturnDTO(
                vehicle.getPlaca(),
                new ClientResponseDTO(vehicle.getClient().getName(), vehicle.getClient().getUserId()),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getCarYear()
        ));

        return ResponseEntity.ok(vehicles);
    }

    public ResponseEntity<?> deleteVehicleById(Long vehicleId, JwtAuthenticationToken jwtAuthenticationToken) {
        var user = userRepository.findById(UUID.fromString(jwtAuthenticationToken.getName())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (vehicle.getClient().getUserId() != user.getUserId()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        vehicleRepository.delete(vehicle);

        return ResponseEntity.ok().build();
    }
}
