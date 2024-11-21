package com.empmngsystem.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments/")
public class DepartmentController {

    @GetMapping("/get")
    public String getWork(){
        return "worked";
    }

}
