package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class StudentController {
	
	@Autowired
	StudentService studentService;
	
	@GetMapping
	public String showHome(){
		return "index";
	}

	@GetMapping("/students")
	public List<StudentDetails> getallStudents(){
		return studentService.findAll();
	}
		
	@PostMapping("/add")
	public StudentDetails addStudents(@RequestBody StudentDetails studentDetails) {

		StudentDetails studentDetails2 = studentService.save(studentDetails);

		return studentDetails2;
		
	}
	
	@PutMapping("/students")
	public StudentDetails updateStudent(@RequestBody StudentDetails studentDetail) {
		return studentService.save(studentDetail);
	}
	
	@GetMapping("/students/{studentID}")
	public Optional<StudentDetails> returnbId(@PathVariable int studentID){
		return studentService.findById(studentID);
	}
	
	@DeleteMapping("/delete/{studentID}")
	public void deletetask(@PathVariable int studentID) {
		
	 studentService.deletebyId(studentID);
	 
	}



}
