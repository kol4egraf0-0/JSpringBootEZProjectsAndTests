package com.demooo.demo.Repository;

import org.springframework.data.repository.CrudRepository;

import com.demooo.demo.Entity.Coffee;

public interface CoffeeRepository extends CrudRepository<Coffee, String> {
}
