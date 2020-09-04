package com.korolkovrs.lesson5;

public class Main {
    public static void main(String[] args) {
        FuelStation fuelStation = new FuelStation();
        Road road = new Road(fuelStation);

        Car car1 = new Car("Volvo XC60", 10);
        Car car2 = new Car("Toyota Camry", 15);
        Car car3 = new Car("Skoda octavia", 12);
        Truck truck1 = new Truck("KAMAZ", 50);
        Truck truck2 = new Truck("Man", 50);
        Truck truck3 = new Truck("Volvo", 30);
        Bus bus1 = new Bus("542", 25);
        Bus bus2 = new Bus("7", 34);
        Bus bus3 = new Bus("34", 45);

        road.addVehice(car1);
        road.addVehice(car2);
        road.addVehice(car3);
        road.addVehice(truck1);
        road.addVehice(truck2);
        road.addVehice(truck3);
        road.addVehice(bus1);
        road.addVehice(bus2);
        road.addVehice(bus3);

//        Car car1 = new Car("1", 10);
//        Car car2 = new Car("2", 10);
//        Car car3 = new Car("3", 10);
//        Car car4 = new Car("4", 10);
//        Car car5 = new Car("5", 10);
//
//        try {
//            road.addVehice(car1);
//            Thread.sleep(50);
//            road.addVehice(car2);
//            Thread.sleep(50);
//            road.addVehice(car3);
//            Thread.sleep(50);
//            road.addVehice(car4);
//            Thread.sleep(50);
//            road.addVehice(car5);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        road.executorService.shutdown();
    }
}
