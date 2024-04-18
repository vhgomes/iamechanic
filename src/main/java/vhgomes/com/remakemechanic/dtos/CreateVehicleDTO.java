package vhgomes.com.remakemechanic.dtos;

import vhgomes.com.remakemechanic.models.User;

import java.time.Year;

public record CreateVehicleDTO(String placa, String brand, String model, Year carYear) {
}
