package vhgomes.com.remakemechanic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vhgomes.com.remakemechanic.models.Role;
import vhgomes.com.remakemechanic.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Optional<User> findByCpf(String cpf);

    List<User> findUsersByRoleEquals(Set<Role> role);

}
