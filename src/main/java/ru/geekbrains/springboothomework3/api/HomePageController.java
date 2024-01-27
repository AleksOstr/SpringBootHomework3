package ru.geekbrains.springboothomework3.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping("/home")
    public String homePage() {
        return "index";
    }
}
