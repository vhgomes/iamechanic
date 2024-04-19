package vhgomes.com.remakemechanic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vhgomes.com.remakemechanic.models.User;
import vhgomes.com.remakemechanic.models.Vehicle;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findVehicleByClient(User client);
}
