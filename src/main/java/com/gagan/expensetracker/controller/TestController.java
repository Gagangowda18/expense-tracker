package com.gagan.expensetracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gagan.expensetracker.security.SecurityUtils;
@RestController
public class TestController {

    @GetMapping("/test")
    public String testApi() {
        return "✅ API is working!";
    }

    private Long getCurrentUserId() {
    return SecurityUtils.getCurrentUserId();
    }

}
