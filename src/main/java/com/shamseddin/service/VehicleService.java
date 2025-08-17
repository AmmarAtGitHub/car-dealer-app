package com.shamseddin.service;

import com.shamseddin.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    Vehicle addVehicle(Vehicle vehicle);
    void updateVehicle(Vehicle vehicle);
    void deleteVehicle(int id);
    Optional<Vehicle> getVehicleById(int id);
    Optional<Vehicle> getVehicleByVin(String vin);
    List<Vehicle> getAllVehicles();
}
