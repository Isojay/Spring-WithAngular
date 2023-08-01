package com.example.JWT.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class DemoController {

    @GetMapping("/public")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello From the Public Endpoint");
    }

    @GetMapping("/auth/user")
    public ResponseEntity<String> sayOLA(){
        return ResponseEntity.ok("Hello From User Secured ENdpoint");
    }
    @GetMapping("/auth/admin")
    public ResponseEntity<String> sayHallo(){
        return ResponseEntity.ok("Hallo From Admin Secured ENdpoint");
    }

}
