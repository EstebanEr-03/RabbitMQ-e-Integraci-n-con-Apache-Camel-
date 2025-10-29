package com.integracion.camel.first_camel_project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    
    @RequestMapping("/api-info")
    public String apiInfo() {
        return "index.html";
    }
}