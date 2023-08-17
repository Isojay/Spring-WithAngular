package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Entity.Log_Table;
import com.example.AngularSpring.Service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    @GetMapping("/returnAllActivity")
    private List<Log_Table> returnAllActivity(){
        return  logService.findAll();
    }



}
