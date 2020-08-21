package com.tv.springbootserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BtvController {

    @GetMapping("/test")
    public String test() {
        return "test 성공";
    }
    
}
