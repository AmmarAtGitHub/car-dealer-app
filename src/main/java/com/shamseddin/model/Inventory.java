package com.shamseddin.model;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages a collection of vehicles available in the dealership inventory.
 * Supports operations like add, remove, search, and calculating total inventory value.
 */
public class Inventory {

    // List of vehicles currently in the inventory
    private final List<Vehicle> vehicles = new ArrayList<>();

    /**
     * Adds a new vehicle to the inventory.
     * Ensures VIN uniqueness and non-null input.
     *
     * @param vehicle the vehicle to add
     * @throws IllegalArgumentException if vehicle is null
     * @throws IllegalStateException if a vehicle with the same VIN already exists
     */
    public synchronized void addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }

        if (vehicles.stream().anyMatch(v -> v.getVin().equalsIgnoreCase(vehicle.getVin()))) {
            throw new IllegalStateException("Vehicle with VIN " + vehicle.getVin() + " already exists.");
        }

        vehicles.add(vehicle);
    }

    /**
     * Removes a vehicle from the inventory by its VIN.
     *
     * @param vin the VIN of the vehicle to remove
     * @return true if the vehicle was removed, false if not found
     */
    public synchronized boolean removeVehicle(String vin) {
        return vehicles.removeIf(v -> v.getVin().equalsIgnoreCase(vin));
    }

    /**
     * Retrieves an unmodifiable list of all vehicles.
     * Prevents external modification of the internal list.
     *
     * @return list of all vehicles
     */
    public List<Vehicle> getAllVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    /**
     * Finds a vehicle by VIN.
     *
     * @param vin the VIN to search for
     * @return Optional containing the vehicle if found, or empty if not
     */
    public Optional<Vehicle> findByVIN(String vin) {
        return vehicles.stream()
                .filter(v -> v.getVin().equalsIgnoreCase(vin))
                .findFirst();
    }

    /**
     * Finds vehicles by their status.
     *
     * @param status the status to filter by
     * @return list of matching vehicles
     */
    public List<Vehicle> findByStatus(VehicleStatus status) {
        return vehicles.stream()
                .filter(v -> v.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Finds all vehicles within a specified price range.
     *
     * @param minPrice the minimum price (inclusive)
     * @param maxPrice the maximum price (inclusive)
     * @return list of matching vehicles
     */
    public List<Vehicle> findVehiclesInPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return vehicles.stream()
                .filter(v -> v.getAskingPrice().compareTo(minPrice) >= 0 &&
                             v.getAskingPrice().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the total number of vehicles in inventory.
     *
     * @return total vehicle count
     */
    public int getTotalVehicles() {
        return vehicles.size();
    }

    /**
     * Calculates the total value of all vehicles in inventory.
     *
     * @return total value as BigDecimal
     */
    public BigDecimal getTotalInventoryValue() {
        return vehicles.stream()
                .map(Vehicle::getAskingPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
