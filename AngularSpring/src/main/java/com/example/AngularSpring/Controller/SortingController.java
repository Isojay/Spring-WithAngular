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
    public List<StudentDetails> SortingbyColumn(@PathVariable String column) {
        return studentService.findAllwithfield(column);
    }

    @GetMapping("/{pagesize}/{pageNumber}")
    private Page<StudentDetails> pagenation(@PathVariable int pagesize, @PathVariable int pageNumber) {
        Page<StudentDetails> page = studentService.findAllwithpagesize(pageNumber, pagesize);

        int totalPages = page.getTotalPages();
        long totalelements = page.getTotalElements();
        if (totalPages > 2) {
            if (totalelements % 2 == 0) {
                return studentService.findAllwithpagesize(pageNumber, ((int) totalelements) / 2);
            }
        }
        return studentService.findAllwithpagesize(pageNumber, ((int) totalelements + 1) / 2);
    }

    @GetMapping("/{criteria}/{keyword}/{offset}/{pagesize}")
    private Page<StudentDetails> getByCriteria(@PathVariable String criteria,
                                               @PathVariable String keyword,
                                               @PathVariable int pagesize,
                                               @PathVariable int offset) {
        Pageable pageable = PageRequest.of(pagesize,offset);


        switch (criteria) {
            case "Email":
                return studentService.findbyemail(keyword, pageable);
            case "Name":
                return studentService.searchkeyword(keyword, pageable);
            case "Semester":
                return studentService.findbysemester(keyword, pageable);
            default:
                throw new IllegalArgumentException("Invalid search criteria: " + criteria);
        }
    }
/*
    @GetMapping("/Email/{keyword}/{offset}/{pagesize}")
    private Page<StudentDetails> byEmail(@PathVariable String keyword,@PathVariable int pagesize,@PathVariable int offset){
        Pageable pageable = PageRequest.of(pagesize,offset);
        return studentService.findbyemail(keyword,pageable);
    }

    @GetMapping("/Name/{keyword}/{offset}/{pagesize}")
    private Page<StudentDetails> byName(@PathVariable String keyword,@PathVariable int pagesize,@PathVariable int offset){
        Pageable pageable = PageRequest.of(pagesize,offset);
        return studentService.searchkeyword(keyword,pageable);
    }
    @GetMapping("/Semester/{keyword}/{offset}/{pagesize}")
    private Page<StudentDetails> bySemester(@PathVariable String keyword,@PathVariable int pagesize,@PathVariable int offset){
        Pageable pageable = PageRequest.of(pagesize,offset);
        return studentService.findbysemester(keyword,pageable);
    }
*/










}
