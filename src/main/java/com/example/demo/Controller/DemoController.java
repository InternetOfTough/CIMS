package com.example.demo.Controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
    @GetMapping("/maplist")
    public String maplist(Model model){
        model.addAttribute("data", "hello!!");
        return "maplist";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model){
        model.addAttribute("data", "dashboard!!");
        return "dashboard";
    }
}
