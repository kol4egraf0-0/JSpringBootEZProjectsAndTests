package com.demooo.demo.Component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.demooo.demo.Entity.Coffee;
import com.demooo.demo.Repository.CoffeeRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {
    private final CoffeeRepository coffeeRepository;

    public DataLoader(CoffeeRepository coffeeRepository){
        this.coffeeRepository = coffeeRepository;
    }

    @PostConstruct
    private void loadData(){
        this.coffeeRepository.saveAll(List.of(
            new Coffee("Capuccino"),
            new Coffee("Italiano"),
            new Coffee("Bonucci"),
            new Coffee("Cacao")
        ));
    }
}
