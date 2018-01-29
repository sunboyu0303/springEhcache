package com.example.spring.ehcache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by sunboyu on 2018/1/29.
 */
@Controller
public class ViewController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
