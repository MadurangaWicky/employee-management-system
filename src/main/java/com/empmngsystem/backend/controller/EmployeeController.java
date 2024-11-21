package com.empmngsystem.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees/")
public class EmployeeController {
    @GetMapping("/get")
    public String getWork(){
        return "worked";
    }
}
