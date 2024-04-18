package vhgomes.com.remakemechanic.dtos;

import java.util.UUID;

public record UserResponseDTO(String username, String name, String telephone, String cpf, UUID userId) {
}
