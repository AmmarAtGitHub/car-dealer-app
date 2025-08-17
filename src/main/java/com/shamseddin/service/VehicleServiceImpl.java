package com.shamseddin.service;

import com.shamseddin.dao.VehicleDAO;
import com.shamseddin.model.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class VehicleServiceImpl implements VehicleService {
    private final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);
    private final VehicleDAO dao;

    public VehicleServiceImpl(VehicleDAO dao) { this.dao = dao; }

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        // example validation: VIN required
        if (vehicle.getVin() == null || vehicle.getVin().isBlank()) {
            throw new IllegalArgumentException("VIN required");
        }
        Optional<Vehicle> existing = dao.getVehicleByVin(vehicle.getVin());
        if (existing.isPresent()) throw new IllegalArgumentException("vehicle with VIN exists");
        Vehicle created = dao.addVehicle(vehicle);
        logger.info("Added vehicle id {}", created.getId());
        return created;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) { dao.updateVehicle(vehicle); }

    @Override
    public void deleteVehicle(int id) {
        Vehicle vehicle = getVehicleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));
        dao.deleteVehicle(vehicle);
    }

    @Override
    public Optional<Vehicle> getVehicleById(int id) { return dao.getVehicle(id); }

    @Override
    public Optional<Vehicle> getVehicleByVin(String vin) { return dao.getVehicleByVin(vin); }

    @Override
    public List<Vehicle> getAllVehicles() { return dao.getAllVehicles(); }
}
