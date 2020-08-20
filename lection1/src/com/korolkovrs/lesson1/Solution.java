package com.korolkovrs.lesson1;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        System.out.println("****************Task 1****************");
//        Integer[] array1 = {1, 2, 3, 4, 5, 6};
        String[] array1 = {"One", "Two", "Three", "Four", "Five", "Six"};
        System.out.println(Arrays.toString(array1));
        Solution.swap(array1, 0, 5);
        System.out.println(Arrays.toString(array1));

        System.out.println("****************Task 2****************");
        List list = arrayToArrayList(array1);
        System.out.println(list);

        System.out.println("****************Task 3****************");

        Box<Apple> appleBox = new Box<>();
        for (int i = 0; i < 30; i++) {
            appleBox.put(new Apple());
        }

        Box<Orange> orangeBox = new Box<>();
        for (int i = 0; i < 22; i++) {
            orangeBox.put(new Orange());
        }

        Box<Orange> orangeBox2 = new Box<>();
        for (int i = 0; i < 8; i++) {
            orangeBox2.put(new Orange());
        }

        System.out.println(appleBox.getWight());
        System.out.println(orangeBox.getWight());
        System.out.println(appleBox.compare(orangeBox));
        System.out.println(orangeBox.compare(orangeBox2));

        orangeBox.shift(orangeBox2);
        System.out.println(orangeBox.getWight());
        System.out.println(orangeBox2.getWight());

        Box fruit = new Box();
        fruit.put(new Apple());
        fruit.put(new Orange());
        System.out.println(fruit.getWight());

        appleBox.shift(fruit);
        System.out.println(appleBox.getWight());
        System.out.println(fruit.getWight());
    }

    public static <T> void swap(T[] array, int index1, int index2) {
        T temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static <T> ArrayList arrayToArrayList(T[] array) {
        return new ArrayList(Arrays.asList(array));
    }
}
