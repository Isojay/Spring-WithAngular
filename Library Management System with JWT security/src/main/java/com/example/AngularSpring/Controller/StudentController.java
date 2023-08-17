package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Entity.Role;
import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Service.StudentService;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	
	private final StudentService studentService;
	private final PasswordEncoder passwordEncoder;

	public StudentController(StudentService studentService, PasswordEncoder passwordEncoder) {
		this.studentService = studentService;
		this.passwordEncoder = passwordEncoder;
	}

//	@PostConstruct
//	public void edit(){
//		String email= "isongum@gmail.com";
//		StudentDetails studentDetails = studentService.findbyEmail(email) ;
//		if (studentDetails != null){
//			studentDetails.setGoogleActivate(false);
//			studentService.save(studentDetails);
//		}
//	}

	@GetMapping("/students")
	public List<StudentDetails> getallStudents(){
		return studentService.findAll();
	}

	@PostMapping("/add")
	public StudentDetails addStudents(@RequestBody StudentDetails studentDetails) {
		studentDetails.setPassword(passwordEncoder.encode(studentDetails.getPassword()));
		studentDetails.setRole(Role.USER);
		return studentService.save(studentDetails);
	}

	@PutMapping("/add")
	public StudentDetails updateStudent(@RequestBody StudentDetails studentDetail) {
		studentDetail.setRole(Role.USER);
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
	@GetMapping("/{pagesize}/{pageNumber}")
	private Page<StudentDetails> pagenation(@PathVariable int pagesize, @PathVariable int pageNumber) {
		return studentService.findAllwithpagesize(pageNumber, pagesize);
	}

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
