package vhgomes.com.remakemechanic.models;

import jakarta.persistence.*;

import java.time.Year;

@Entity
@Table(name = "tb_vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "vehicle_id")
    private Long vehicleId;
    @Column(unique = true)
    private String placa;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User client;
    private String brand;
    private String model;
    private Year carYear;

    public Vehicle(Long vehicleId, String placa, User client, String brand, String model, Year carYear) {
        this.vehicleId = vehicleId;
        this.placa = placa;
        this.client = client;
        this.brand = brand;
        this.model = model;
        this.carYear = carYear;
    }

    public Vehicle() {
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Year getCarYear() {
        return carYear;
    }

    public void setCarYear(Year carYear) {
        this.carYear = carYear;
    }
}
