package com.shamseddin.dao;

import java.util.List;

import com.shamseddin.model.Vehicle;
import java.sql.SQLException;
import java.util.Optional;

public interface VehicleDAO{


    void addVehicle(Vehicle vehicle);
    void updateVehicle(Vehicle vehicle);
    void deleteVehicle(Vehicle vehicle);
    Optional<Vehicle> getVehicle(int id);
    Optional<Vehicle> getVehicleByVin(String vin);
    List<Vehicle> getAllVehicles();
}
