package fizz.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FizzBuzzServiceTest {
    private static final FizzBuzzService service = new FizzBuzzService();

    @Test
    @DisplayName("Should return Fizz when divisible by 3")
    void testFizz() {
        String fizz = "Fizz";
        assertAll(
                () -> assertService(3, fizz),
                () -> assertService(6, fizz),
                () -> assertService(12, fizz)
        );
    }

    @Test
    @DisplayName("Should return Buzz when divisible by 5")
    void testBuzz() {
        String buzz = "Buzz";
        assertAll(
                () -> assertService(5, buzz),
                () -> assertService(10, buzz),
                () -> assertService(20, buzz)
        );
    }

    @Test
    @DisplayName("Should return FizzBuzz when divisible by 3 and 5")
    void testFizzBuzz() {
        String fizzBuzz = "FizzBuzz";
        assertAll(
                () -> assertService(0, fizzBuzz),
                () -> assertService(15, fizzBuzz),
                () -> assertService(30, fizzBuzz),
                () -> assertService(45, fizzBuzz)
        );
    }

    @Test
    @DisplayName("Should return number when not divisible by 3 and 5")
    void testOther() {
        assertAll(
                () -> assertService(1, "1"),
                () -> assertService(2, "2"),
                () -> assertService(4, "4")
        );
    }

    private void assertService(int number, String expected) {
        assertEquals(service.getFizzBuzz(number), expected, "Expected FizzBuzz for " + number);
    }
}
