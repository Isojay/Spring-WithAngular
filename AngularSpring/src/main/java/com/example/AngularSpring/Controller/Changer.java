package com.example.AngularSpring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Service.StudentService;

@Controller
public class Changer {
	
	@Autowired
	StudentService studentService;
	
	@GetMapping("/add")
	public String addDetails() {
		return "addStudents";
	}
	
	@GetMapping("/update")
	public String updateStudent(@RequestParam int id,Model model) {
		
		model.addAttribute("student",studentService.findById(id));
		
		return "updateStudent";
	}
	
	@PostMapping("/save")
	public String saveString(@ModelAttribute("student") StudentDetails studentDetails) {
		
		studentService.savestd(studentDetails);
		
		return "index";
	}
	
}
