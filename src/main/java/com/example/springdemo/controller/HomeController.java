package com.example.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String main() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(ModelMap modelMap) {
        List<String> names = new ArrayList<>();
        names.add("Poxos");
        names.add("Valod");
        names.add("Misak");
        names.add("Petros");
        names.add("Martiros");
        modelMap.addAttribute("names", names);
        return "home";
    }
}
