package com.learn.demo.mall.gateway.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo
 * @author zh_cr
 */
@RestController
@CrossOrigin
@RequestMapping("/")
public class DemoController {

    @GetMapping
    public String welcome() {
        return "Welcome to mall!";
    }
}
