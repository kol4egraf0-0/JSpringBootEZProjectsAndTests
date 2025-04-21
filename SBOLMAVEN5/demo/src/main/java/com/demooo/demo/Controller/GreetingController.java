package com.demooo.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demooo.demo.Properties.Greeting;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
    
    private final Greeting greeting;

    public GreetingController(Greeting greeting){
        this.greeting = greeting;
    }
    
    @GetMapping
    String getGreeting() {
        return greeting.getName();
    }

    @GetMapping("/coffee")
    String getNameAndCoffee() {
        return greeting.getCoffee();
    }
}
