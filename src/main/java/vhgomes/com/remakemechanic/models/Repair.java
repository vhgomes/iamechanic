package vhgomes.com.remakemechanic.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "tb_repairs")
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long repairId;
    @JoinColumn(name = "mechanic_id")
    @OneToOne
    private User mechanicId;
    @JoinColumn(name = "vehicle_id")
    @OneToOne
    private Vehicle vehicleId;
    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
    private BigDecimal value;

    @OneToMany(mappedBy = "repair")
    private Set<Ticket> tickets;


    public Repair(long repairId, User mechanicId, Vehicle vehicleId, Instant createdAt, Instant updatedAt, BigDecimal value, Set<Ticket> tickets) {
        this.repairId = repairId;
        this.mechanicId = mechanicId;
        this.vehicleId = vehicleId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.value = value;
        this.tickets = tickets;
    }

    public Repair() {
    }

    public long getRepairId() {
        return repairId;
    }

    public void setRepairId(long repairId) {
        this.repairId = repairId;
    }

    public User getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(User mechanicId) {
        this.mechanicId = mechanicId;
    }

    public Vehicle getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Vehicle vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
