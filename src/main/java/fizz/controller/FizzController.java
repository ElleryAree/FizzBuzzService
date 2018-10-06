package fizz.controller;

import fizz.model.FizzBuzzResponse;
import fizz.service.FizzBuzzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FizzController {
    private final FizzBuzzService fizzBuzzService;

    @Autowired
    public FizzController(FizzBuzzService fizzBuzzService) {
        this.fizzBuzzService = fizzBuzzService;
    }

    @RequestMapping("/fizz/{number}")
    public FizzBuzzResponse getFizz(@PathVariable int number) {
        return new FizzBuzzResponse(number, fizzBuzzService.getFizzBuzz(number));
    }
}