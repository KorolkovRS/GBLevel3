package ru.korolkovrs.lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class MyArrayTest {

    @Test
    public void checkOutputArray1(){
        Assertions.assertArrayEquals(MyArray.elementsAfterLastFour(new int[] {1,3,2,4,5,62,2}), new int[] {5,62,2});
    }

    @Test
    public void checkOutputArray2(){
        Assertions.assertArrayEquals(MyArray.elementsAfterLastFour(new int[] {1,3,2,4,5,62,4}), new int[] {});
    }

    @Test
    public void checkOutputArray3(){
        Assertions.assertArrayEquals(MyArray.elementsAfterLastFour(new int[] {4,1,3,2,5,62}), new int[] {1,3,2,5,62});
    }

    @Test
    public void checkInputArrayWithoutFour() {
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                MyArray.elementsAfterLastFour(new int[] {1,3,2,5,62});
            }
        });
    }

    @Test
    public void checkContainsOneOrFourInArray1() {
        Assertions.assertEquals(MyArray.containOneOrFour(new int[] {1,3,2,5,62}), true);
    }

    @Test
    public void checkContainsOneOrFourInArray2() {
        Assertions.assertEquals(MyArray.containOneOrFour(new int[] {3,2,5,62,4}), true);
    }

    @Test
    public void checkContainsOneOrFourInArray3() {
        Assertions.assertEquals(MyArray.containOneOrFour(new int[] {}), false);
    }

    @Test
    public void checkContainsOneOrFourInArray4() {
        Assertions.assertEquals(MyArray.containOneOrFour(new int[] {6,3,2,5,62}), false);
    }
}
