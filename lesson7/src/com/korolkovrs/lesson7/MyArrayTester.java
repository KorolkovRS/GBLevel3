package com.korolkovrs.lesson7;


public class MyArrayTester {
    private MyArray myArray;
    private boolean testResult = true;

    @BeforeSuit
    public void prepareData() {
        myArray = new MyArray();
        System.out.println("Create new Array");
    }

    @AfterSuit
    public void deleteData() {
        if (testResult) {
            System.out.println("All tests success!");
        } else {
            System.out.println("Fail");
        }
    }

    @Test (priority = 10)
    public void containOneOrFourTest1() {
        myArray.setArray(new int[] {1,2,5,6,4,78,5});

        if (myArray.containOneOrFour()) {
            System.out.println("Test 1 success");
            return;
        }
        System.out.println("Test 1 fail");
        testResult = false;
    }

    @Test (priority = 8)
    public void containOneOrFourTest2() {
        myArray.setArray(new int[] {2,5,6,78,5});

        if (!myArray.containOneOrFour()) {
            System.out.println("Test 2 success");
            return;
        }
        System.out.println("Test 2 fail");
        testResult = false;
    }

    @Test
    public void containOneOrFourTest3() {
        myArray.setArray(new int[] {2,5,6,78,5});

        if (!myArray.containOneOrFour()) {
            System.out.println("Test 3 success");
            return;
        }
        System.out.println("Test 3 fail");
        testResult = false;
    }

    @Test (priority = 1)
    public void containOneOrFourTest4() {
        myArray.setArray(new int[] {2,5,6,4,78,5});

        if (!myArray.containOneOrFour()) {
            System.out.println("Test 4 success");
            return;
        }
        System.out.println("Test 4 fail");
        testResult = false;
    }

    @Test
    public void containOneOrFourTest5() {
        myArray.setArray(new int[] {2,5,6,78,5});

        if (!myArray.containOneOrFour()) {
            System.out.println("Test 5 success");
            return;
        }
        System.out.println("Test 5 fail");
        testResult = false;
    }
}
