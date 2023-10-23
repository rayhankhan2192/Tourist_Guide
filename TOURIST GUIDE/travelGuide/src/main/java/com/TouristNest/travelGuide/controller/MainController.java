package com.TouristNest.travelGuide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/homepage")
    public String homepage(){
        return "Homepage";
    }
    @RequestMapping("/test")
    public String test(){
        return "Test";
    }
    @RequestMapping("/berlin")
    public String berlin(){
        return "Berlin";
    }
}
