package com.example.AngularSpring.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Redirect {

    @GetMapping("/Students")
    public String showStudent(){
        return "Student";
    }
}
