package com.korolkovrs.lesson5;

import java.util.concurrent.*;

public class FuelStation {
    private final int CAPACITY = 3;
    private Semaphore semaphore = new Semaphore(CAPACITY, true);

    public Future<Float> connectToFuelStation(RoadDriveVehicle vehicle) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        try {
            System.out.println(vehicle + " ожидает заправки");
            semaphore.acquire();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Future<Float> floatFuture = executorService.submit(new RefuelService(vehicle));
        executorService.shutdown();
        return floatFuture;
    }

    public class RefuelService implements Callable {
        private RoadDriveVehicle vehicle;

        public RefuelService(RoadDriveVehicle vehicle) {
            this.vehicle = vehicle;
        }

        @Override
        public Object call() throws Exception {
            return refuel(vehicle);
        }

        public float refuel(RoadDriveVehicle vehicle) {
            float gazoline = 0;
            try {
                System.out.println(vehicle + " начала заправку");
                Thread.sleep(5000);
                gazoline = vehicle.fuelRequired();
                System.out.println(vehicle + " заправилась");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            semaphore.release();
            return gazoline;
        }
    }
}
