package fizz.service;

import org.springframework.stereotype.Component;

@Component
public class FizzBuzzService {
    public String getFizzBuzz(int number) {
        var fizz = number % 3 == 0;
        var buzz = number % 5 == 0;

        if (fizz && buzz) {
            return "FizzBuzz";
        }
        if (fizz) {
            return "Fizz";
        }
        if (buzz) {
            return "Buzz";
        }
        return "" + number;
    }
}
