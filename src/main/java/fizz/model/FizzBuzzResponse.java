package fizz.model;

public class FizzBuzzResponse {

    private final int number;
    private final String response;

    public FizzBuzzResponse(int number, String response) {
        this.number = number;
        this.response = response;
    }

    public int getNumber() {
        return number;
    }

    public String getResponse() {
        return response;
    }
}
