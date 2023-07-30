package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Service.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentController {
	
	final
	StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public ModelAndView showHome(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@GetMapping("/students")
	public List<StudentDetails> getallStudents(){
		return studentService.findAll();
	}
		
	@PostMapping("/add")
	public StudentDetails addStudents(@RequestBody StudentDetails studentDetails) {
		return studentService.save(studentDetails);
	}
	
	@PutMapping("/add")
	public StudentDetails updateStudent(@RequestBody StudentDetails studentDetail) {

		return studentService.save(studentDetail);
	}
	
	@GetMapping("/update")
	public Optional<StudentDetails> returnbyId(@RequestParam int studentID){
		return studentService.findById(studentID);
	}
	
	@DeleteMapping("/delete/{studentID}")
	public void deletetask(@PathVariable int studentID) {
		
	 studentService.deletebyId(studentID);
	 
	}



}
