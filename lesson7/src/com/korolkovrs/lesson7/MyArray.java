package com.korolkovrs.lesson7;

import java.util.Arrays;

public class MyArray extends TestableClass{
    private int[] array;

    public MyArray() {

    }

    public MyArray(int[] array) {
        this.array = array;
    }

    public int[] elementsAfterLastFour() {
        int aSize = 0;
        int from = 0;
        for (int i = array.length - 1; i >= 0; i--, aSize++) {
            if (array[i] == 4) {
                from = array.length - aSize;
                break;
            }
        }
        if (from == 0) {
            throw new RuntimeException("Array should contain 4");
        }
        return Arrays.copyOfRange(array, from, array.length);
    }

    public boolean containOneOrFour() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1 || array[i] == 4) {
                return true;
            }
        }
        return false;
    }

    public void setArray(int[] array) {
        this.array = array;
    }
}
