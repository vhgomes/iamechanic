package vhgomes.com.remakemechanic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vhgomes.com.remakemechanic.models.Repair;

@Repository

public interface RepairRepository extends JpaRepository<Repair, Long> {
}
