package com.korolkovrs.lesson5;

public class Car extends RoadDriveVehicle {
    private String name;

    public Car(String name, float fuelSupply) {
        super(20, fuelSupply, 2.5f);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                '}';
    }
}
