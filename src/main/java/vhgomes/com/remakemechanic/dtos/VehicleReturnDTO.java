package vhgomes.com.remakemechanic.dtos;

import java.time.Year;

public record VehicleReturnDTO(String placa, ClientResponseDTO cliente, String brand, String model, Year carYear) {
}
