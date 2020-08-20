package com.korolkovrs.lesson1;

import java.util.ArrayList;

public class Box <T extends Fruit> {
    private ArrayList<T> fruits = new ArrayList<>();
    private double wight;

    public double getWight() {
        return wight;
    }

    public void put(T fruit) {
        fruits.add(fruit);
        wight += fruit.getWight();
    }

    public void shift(Box other) {
        for (T fruit : fruits) {
            other.put(fruit);
        }
        fruits.clear();
        wight = 0;
    }

    public boolean compare(Box other) {
        if (this == other) {
            return true;
        }
        if (Math.abs(this.wight - other.wight) > 0.001) {
            return false;
        }
        return true;
    }
}
