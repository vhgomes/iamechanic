package vhgomes.com.remakemechanic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vhgomes.com.remakemechanic.models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
