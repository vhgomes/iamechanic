package vhgomes.com.remakemechanic.dtos;

import java.util.UUID;

public record ClientsResponseDTO(String username, String name, String telephone, String cpf, UUID userId) {
}
