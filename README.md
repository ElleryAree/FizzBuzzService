# FizzBuzzService

Web service running fizz-buzz solution.

Run with:
```
gradlew bootRun
```

To make a request (port is set in properties to 8090):
```
localhost:8090/fizz?number=<int>
```
Response:
```
{"number": <original_number>, "response": "<Fizz/Buzz/FizzBuzz/<original_number>"}
```
If <int> was not an int, for example, string or float, 404 will be returned.
