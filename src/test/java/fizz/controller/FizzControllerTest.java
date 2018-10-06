package fizz.controller;

import fizz.service.FizzBuzzService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
public class FizzControllerTest {
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        FizzBuzzService fizzBuzzService = new FizzBuzzService();

        this.mvc = MockMvcBuilders.standaloneSetup(new FizzController(fizzBuzzService)).build();
    }

    @Test
    @DisplayName("Should return number when it's not fizz/buzz")
    void otherTest() throws Exception {
        doGet("/fizz?number=7")
                .andExpect(status().isOk())
                .andExpect(content().json("{\"number\": 7, \"response\": \"7\"}"));
    }

    @Test
    @DisplayName("Should return fizz when appropriate")
    void fizzTest() throws Exception {
        doGet("/fizz?number=3")
                .andExpect(status().isOk())
                .andExpect(content().json("{\"number\": 3, \"response\": \"Fizz\"}"));
    }

    @Test
    @DisplayName("Should return 404 when number is not actually a number")
    void stringTest() throws Exception {
        doGet("/fizz?number=hello")
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Should return 404 when number is not an integer")
    void doubleTest() throws Exception {
        doGet("/fizz?number=1.3")
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Should return 404 when number is not provided")
    void emptyTest() throws Exception {
        doGet("/fizz")
                .andExpect(status().is4xxClientError());
    }

    private ResultActions doGet(String path) throws Exception {
        return this.mvc.perform(get(path));
    }
}
