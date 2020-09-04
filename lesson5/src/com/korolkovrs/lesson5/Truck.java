package com.korolkovrs.lesson5;

public class Truck extends RoadDriveVehicle {
    private String name;

    public Truck(String name, float fuelSupply) {
        super(60, fuelSupply, 15f);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "name='" + name + '\'' +
                '}';
    }
}
