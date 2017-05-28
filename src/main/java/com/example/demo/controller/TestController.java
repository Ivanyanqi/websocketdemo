package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description :
 * Date : 2017/5/19
 * Time : 20:24
 *
 * @author : 仙人球
 */
@RestController
public class TestController {

    @Value("${name}")
    private String name;

    @GetMapping("/test")
    public String test(){
        return name;
    }
}
