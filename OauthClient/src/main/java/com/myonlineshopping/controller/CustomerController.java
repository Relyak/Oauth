package com.myonlineshopping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @RequestMapping(value = "/customer")
    public String customer() {
        return "customer";
    }
}