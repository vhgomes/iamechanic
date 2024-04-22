package vhgomes.com.remakemechanic.dtos;

import java.math.BigDecimal;
import java.time.Instant;

public record RepairResponseDTO(Long repairId, MechanicResponseDTO mechanic, Long vehicle, Instant createdAt, BigDecimal valor) {
}
