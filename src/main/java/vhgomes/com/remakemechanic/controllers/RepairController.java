package vhgomes.com.remakemechanic.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vhgomes.com.remakemechanic.dtos.CreateRepairDTO;
import vhgomes.com.remakemechanic.services.RepairService;

@RestController
@RequestMapping("/api/repair")
public class RepairController {
    private final RepairService repairService;

    public RepairController(RepairService repairService) {
        this.repairService = repairService;
    }

    @PostMapping
    public ResponseEntity<?> createRepair(CreateRepairDTO createRepairDTO, JwtAuthenticationToken jwtAuthenticationToken) {
        return repairService.createRepair(createRepairDTO, jwtAuthenticationToken);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_MECHANIC') or hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<?> getAll() {
        return repairService.getAllRepairs();
    }
}
