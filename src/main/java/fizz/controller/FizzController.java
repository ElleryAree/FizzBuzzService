package fizz.controller;

import fizz.model.FizzBuzzResponse;
import fizz.service.FizzBuzzService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FizzController {
    private final FizzBuzzService fizzBuzzService;
    private static final Logger logger = LogManager.getLogger(FizzController.class);

    @Autowired
    public FizzController(FizzBuzzService fizzBuzzService) {
        // Actually, it's better to have an interface here,
        // so it can be easily mocked, but for such a trivial service it doesn't make sense.
        this.fizzBuzzService = fizzBuzzService;
    }

    @RequestMapping("/fizz")
    public FizzBuzzResponse getFizz(@RequestParam(value="number") int number) {
        logger.info(() -> "Recieved request for number " + number);

        return new FizzBuzzResponse(number, fizzBuzzService.getFizzBuzz(number));
    }
}