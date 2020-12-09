package com.fwm.restaurante.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String get(){
        return "Get api FWM para restaurante";
    }
    @PostMapping
    public String post(){
        return "Post api FWM para restaurante";
    }
    @PutMapping
    public String put(){
        return "Put api FWM para restaurante";
    }
    @DeleteMapping
    public String delete(){
        return "Delete api FWM para restaurante";
    }
}
