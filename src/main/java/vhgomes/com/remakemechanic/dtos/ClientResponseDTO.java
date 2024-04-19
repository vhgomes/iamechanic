package vhgomes.com.remakemechanic.dtos;

import vhgomes.com.remakemechanic.models.User;

import java.util.UUID;

public record ClientResponseDTO(String name, UUID userId) {
}
