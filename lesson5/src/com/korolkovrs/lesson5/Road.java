package com.korolkovrs.lesson5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Road {
    public ExecutorService executorService;
    FuelStation fuelStation;

    public Road(FuelStation fuelStation) {
        this.fuelStation = fuelStation;
        executorService = Executors.newFixedThreadPool(100);
    }

    public void addVehice(RoadDriveVehicle vehicle) {
        vehicle.goToRoad(this);
        executorService.execute(vehicle);
    }

    public FuelStation getFuelStation() {
        return fuelStation;
    }
}
