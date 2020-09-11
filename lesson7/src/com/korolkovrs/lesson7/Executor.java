package com.korolkovrs.lesson7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Executor {
    private Class aClass;
    private Object tester;
    private boolean exceptionFlag;

    public Executor(Class aClass) {
        this.aClass = aClass;
    }

    public void execute() {
        executeBeforeSuitMethods();
        executeTests();
        executeAfterSuitMethods();
    }

    private void executeBeforeSuitMethods() {
        exceptionFlag = false;
        for (Method method : aClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuit.class)) {
                if (!exceptionFlag) {
                    try {
                        tester = aClass.newInstance();
                        method.invoke(tester);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new RuntimeException("Invalid number of methods with annotation @BeforeSuit. The Max number is 1");
                }
                exceptionFlag = true;
            }
        }
    }

    private void executeTests() {
        ArrayList<Integer> priorityList = createPriorityList();
        HashMap<Method, Integer> priorityMap = new HashMap<>();

        for (Integer priority : priorityList) {
            for (Method method : aClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Test.class)) {
                    if (method.getAnnotation(Test.class).priority() == priority) {
                        try {
                            method.invoke(tester);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        for (Integer priority : priorityList) {
            for (Map.Entry<Method, Integer> test : priorityMap.entrySet()) {
                if (test.getValue() == priority) {
                    try {
                        test.getKey().invoke(tester);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void executeAfterSuitMethods() {
        exceptionFlag = false;
        for (Method method : aClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AfterSuit.class)) {
                if (!exceptionFlag) {
                    try {
                        method.invoke(tester);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } else {
                    throw new RuntimeException("Invalid number of methods with annotation @AfterSuit. The Max number is 1");
                }
                exceptionFlag = true;
            }
        }
    }

    private ArrayList<Integer> createPriorityList() {
        HashSet<Integer> prioritySet = new HashSet<>();

        for (Method method : aClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
                Test anno = method.getAnnotation(Test.class);
                prioritySet.add(anno.priority());
            }
        }

        ArrayList<Integer> priorityList = new ArrayList<>(prioritySet);
        Collections.sort(priorityList, Collections.reverseOrder());
        System.out.println(priorityList);

        return priorityList;
    }
}
