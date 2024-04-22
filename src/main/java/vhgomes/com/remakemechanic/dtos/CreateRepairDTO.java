package vhgomes.com.remakemechanic.dtos;

import vhgomes.com.remakemechanic.models.User;
import vhgomes.com.remakemechanic.models.Vehicle;

import java.math.BigDecimal;

public record CreateRepairDTO(Vehicle vehicle, User mechanic, BigDecimal valor) {
}
