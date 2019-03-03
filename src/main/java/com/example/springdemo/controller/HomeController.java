package com.example.springdemo.controller;

import com.example.springdemo.model.User;
import com.example.springdemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author Rafik Gasparyan 02/03/2019
 */
@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    /**
     * @return redirect to home page
     */
    @GetMapping("/")
    public String main() {
        return "redirect:/home";
    }

    /**
     * this method for get all users from db
     *
     * @param modelMap send all users list to home page
     * @return home page
     */
    @GetMapping("/home")
    public String home(ModelMap modelMap) {
        List<User> all = userRepository.findAll();
        modelMap.addAttribute("users", all);
        return "home";
    }
}