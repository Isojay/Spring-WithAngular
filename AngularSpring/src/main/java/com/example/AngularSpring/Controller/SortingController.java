package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class SortingController {

    @Autowired
    StudentService studentService;

    @GetMapping("/{column}")
    public List<StudentDetails> SortingbyColumn(@PathVariable String column){
        return studentService.findAll(column);
    }




}
