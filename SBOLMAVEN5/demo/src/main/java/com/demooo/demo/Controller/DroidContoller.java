package com.demooo.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demooo.demo.Entity.Droid;


@RestController
@RequestMapping("/droid")
public class DroidContoller {
    private final Droid droid;

    public DroidContoller(Droid droid){
        this.droid = droid;
    } 

    @GetMapping
    Droid getDroid() {
        return droid;
    }
}
