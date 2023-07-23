package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SortingController {

    final
    StudentService studentService;

    public SortingController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{column}")
    public List<StudentDetails> SortingbyColumn(@PathVariable String column){
        return studentService.findAllwithfield(column);
    }

    @GetMapping("/{pagesize}/{offset}")
    private Page<StudentDetails> pagenation(@PathVariable int pagesize,@PathVariable int offset){
        return studentService.findAllwithpagesize(offset,pagesize);
    }

    @GetMapping("/email/{keyword}/{offset}/{pagesize}")
    private Page<StudentDetails> byEmail(@PathVariable String keyword,@PathVariable int pagesize,@PathVariable int offset){
        Pageable pageable = PageRequest.of(pagesize,offset);
        return studentService.findbyemail(keyword,pageable);
    }

    @GetMapping("/name/{keyword}/{offset}/{pagesize}")
    private Page<StudentDetails> byName(@PathVariable String keyword,@PathVariable int pagesize,@PathVariable int offset){
        Pageable pageable = PageRequest.of(pagesize,offset);
        return studentService.searchkeyword(keyword,pageable);
    }
    @GetMapping("/semester/{keyword}/{offset}/{pagesize}")
    private Page<StudentDetails> bySemester(@PathVariable String keyword,@PathVariable int pagesize,@PathVariable int offset){
        Pageable pageable = PageRequest.of(pagesize,offset);
        return studentService.findbysemester(keyword,pageable);
    }



}
