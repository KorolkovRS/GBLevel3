package com.korolkovrs.lesson5;

import java.util.concurrent.Future;

abstract class RoadDriveVehicle implements Runnable {
    private Road road;

    private float maxFuelSupply;
    private float fuelSupply;
    private float fuelConsumption;

    public RoadDriveVehicle(float maxFuelSupply, float fuelSupply, float fuelConsumption) {
        this.maxFuelSupply = maxFuelSupply;
        setFuelSupply(fuelSupply);
        this.fuelConsumption = fuelConsumption;
    }

    public void setFuelSupply(float fuelSupply) {
        if (fuelSupply <= maxFuelSupply && fuelSupply >= 0) {
            this.fuelSupply = fuelSupply;
        } else if (fuelSupply < 0) {
            this.fuelSupply = 0;
        } else {
            this.fuelSupply = maxFuelSupply;
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            while (fuelSupply > fuelConsumption) {
                try {
                    Thread.sleep(300);
                    fuelSupply -= fuelConsumption;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            FuelStation fuelStation = road.getFuelStation();

            if (road != null && fuelStation != null) {
                goToRefuel(fuelStation);
            }
            System.out.printf("%s прошел %dй круг\n", this, i + 1);
        }
    }

    public void goToRoad(Road road) {
        this.road = road;
    }

    public float fuelRequired() {
        return maxFuelSupply - fuelSupply;
    }

    public void goToRefuel(FuelStation fuelStation) {
        Future<Float> fuel = fuelStation.connectToFuelStation(this);
        try {
            fuelSupply = fuel.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


