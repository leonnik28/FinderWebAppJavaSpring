package com.dota.personaji.dota2.controller;

import com.dota.personaji.dota2.service.RequestCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/request")
public class RequestController {
    private final RequestCounterService requestCounterService;

    @Autowired
    public RequestController(RequestCounterService requestCounterService) {
        this.requestCounterService = requestCounterService;
    }

    @GetMapping()
    public int requestCount(){
        return requestCounterService.getCounter();
    }
}
