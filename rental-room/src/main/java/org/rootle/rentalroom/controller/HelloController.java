package org.rootle.rentalroom.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class HelloController {
    /**
     * HTTP GET requests to the root path ("/") and returns greeting message.
     *
     * @return message "Greetings from Spring Boot!!".
     */

    @GetMapping("hello")
    public String index() {
        return "Greetings from Spring Boot!!";
    }
}
