package com.korolkovrs.lesson5;

public class Bus extends RoadDriveVehicle {
    private String name;

    public Bus(String name, float fuelSupply) {
        super(40, fuelSupply, 7.5f);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "name='" + name + '\'' +
                '}';
    }
}
