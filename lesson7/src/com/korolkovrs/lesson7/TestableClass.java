package com.korolkovrs.lesson7;

public class TestableClass {
    public static void start(String className) {
        System.out.println("Start with name");
        try {
            Class tester = Class.forName(className);
            new Executor(tester).execute();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void start(Class tester) {
        System.out.println("Start with class");
        new Executor(tester).execute();
    }
}
