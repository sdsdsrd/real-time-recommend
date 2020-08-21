package com.tv.springbootserver.controller;

import com.tv.springbootserver.dto.LogDto;
import com.tv.springbootserver.service.BtvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BtvController {

    @Autowired
    private BtvService service;

    @GetMapping("/log")
    public List<LogDto> getLog() {
        return service.getLog();
    }
    
}
