package ru.korolkovrs.lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MyArrayTest {

    @ParameterizedTest
    @MethodSource("arrayStream")
    public void checkOutputArray(int[] array, int[] result) {
        Assertions.assertArrayEquals(MyArray.elementsAfterLastFour(array), result);
    }

    static Stream<Arguments> arrayStream() {
        List<Arguments> arguments = Arrays.asList(
                Arguments.of(new int[] {1,3,2,4,5,62,2}, new int[] {5,62,2}),
                Arguments.of(new int[] {1,3,2,4,5,62,4}, new int[] {}),
                Arguments.of(new int[] {4,1,3,2,5,62}, new int[] {1,3,2,5,62})
                );
               return arguments.stream();
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

    @ParameterizedTest
    @MethodSource("arrayAndStatementSteam")

    public void checkContainsOneOrFourInArray(int[] array, boolean statement) {
        Assertions.assertEquals(MyArray.containOneOrFour(array), statement);
    }

    static Stream<Arguments> arrayAndStatementSteam() {
        List<Arguments> arguments = Arrays.asList(
                Arguments.of(new int[] {1,3,2,5,62}, true),
                Arguments.of(new int[] {3,2,5,62,4}, true),
                Arguments.of(new int[] {}, false),
                Arguments.of(new int[] {6,3,2,5,62}, false)
        );
        return arguments.stream();
    }
}
