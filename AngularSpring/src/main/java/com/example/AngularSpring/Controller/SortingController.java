package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SortingController {
    final
    StudentService studentService;
    public SortingController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{pagesize}/{pageNumber}")
    private Page<StudentDetails> pagenation(@PathVariable int pagesize, @PathVariable int pageNumber) {
       return studentService.findAllwithpagesize(pageNumber, pagesize);
    }

/*
    @GetMapping("/{offset}/{pagesize}/{fName}/{email}/{semester}")
    private Page<StudentDetails> getByfNamesem(@PathVariable int pagesize,
                                              @PathVariable int offset,
                                               @PathVariable String email,
                                               @PathVariable String fName,
                                              @PathVariable String semester) {
        Pageable pageable = PageRequest.of(pagesize,offset);
        if (fName != null && email!=null && semester!=null){
            return studentService.findbyemailfnamesemester(fName,email,semester,pageable);
        } else if (fName != null && email != null) {
            return studentService.findbyemailfname(fName,email,pageable);
        } else if (email != null && semester != null) {
            return studentService.findbyemailsemester(email,semester,pageable);
        } else if (email==null && fName != null && semester!=null) {
            return  studentService.findbyfnamesemester(fName,semester,pageable);
        } else if (email != null) {
            return  studentService.findbyemail(email,pageable);
        } else if (fName != null) {
            return  studentService.searchkeyword(fName,pageable);
        } else if (semester != null) {
            return studentService.findbysemester(semester,pageable);
        }else {
            return studentService.findAllwithpagesize(offset, pagesize);
        }

    }
*/
@GetMapping("/search")
private Page<StudentDetails> searchStudents(@RequestParam(required = false) String email,
                                            @RequestParam(required = false) String fName,
                                            @RequestParam(required = false) String semester,
                                            @RequestParam(defaultValue = "0") int offset,
                                            @RequestParam(defaultValue = "3") int pagesize) {
    Pageable pageable = PageRequest.of(offset, pagesize);
    if ((fName != null) && (email != null) && (semester != null)) {
        return studentService.findbyemailfnamesemester(fName, email, semester, pageable);
    } else if (fName != null && email != null) {
        return studentService.findbyemailfname(fName, email, pageable);
    } else if (email != null && semester != null) {
        return studentService.findbyemailsemester(email, semester, pageable);
    } else if (email == null && fName != null && semester != null) {
        return studentService.findbyfnamesemester(fName, semester, pageable);
    } else if (email != null) {
        return studentService.findbyemail(email, pageable);
    } else if (fName != null) {
        return studentService.searchkeyword(fName, pageable);
    } else if (semester != null) {
        return studentService.findbysemester(semester, pageable);
    } else {
        return studentService.findAllwithpagesize(offset, pagesize);
    }
}












}
