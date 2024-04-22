package vhgomes.com.remakemechanic.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import vhgomes.com.remakemechanic.dtos.CreateRepairDTO;
import vhgomes.com.remakemechanic.dtos.MechanicResponseDTO;
import vhgomes.com.remakemechanic.dtos.RepairResponseDTO;
import vhgomes.com.remakemechanic.models.Repair;
import vhgomes.com.remakemechanic.repositories.RepairRepository;
import vhgomes.com.remakemechanic.repositories.UserRepository;
import vhgomes.com.remakemechanic.repositories.VehicleRepository;

import java.util.UUID;

@Service
public class RepairService {
    private final RepairRepository repairRepository;

    private final VehicleRepository vehicleRepository;

    private final UserRepository userRepository;

    public RepairService(RepairRepository repairRepository, VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.repairRepository = repairRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> createRepair(CreateRepairDTO createRepairDTO, JwtAuthenticationToken jwtAuthenticationToken) {
        var vehicle = vehicleRepository.findById(createRepairDTO.vehicle().getVehicleId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        var mechanic = userRepository.findById(UUID.fromString(jwtAuthenticationToken.getName())).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Repair repairToSave = new Repair();
        repairToSave.setMechanicId(mechanic);
        repairToSave.setValue(createRepairDTO.valor());
        repairToSave.setVehicleId(vehicle);

        repairRepository.save(repairToSave);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> getAllRepairs() {
        var repairs = repairRepository.findAll().stream().map(repair -> new RepairResponseDTO(
                repair.getRepairId(),
                new MechanicResponseDTO(repair.getMechanicId().getName(), repair.getMechanicId().getUserId()),
                repair.getVehicleId().getVehicleId(),
                repair.getCreatedAt(),
                repair.getValue()
        ));

        return ResponseEntity.ok(repairs);
    }

}
