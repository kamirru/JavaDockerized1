package com.kamilrak.basicweb;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {


    @GetMapping
    public String getBasicResponse() {
        return "Hello World";
    }
}
