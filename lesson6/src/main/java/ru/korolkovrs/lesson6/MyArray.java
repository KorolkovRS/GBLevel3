package ru.korolkovrs.lesson6;

import java.util.Arrays;

public class MyArray {
    public static int[] elementsAfterLastFour(int[] array) {
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

    public static boolean containOneOrFour(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1 || array[i] == 4) {
                return true;
            }
        }
        return false;
    }
}