package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Repo.StudentRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class StudentService {
	
	final
    StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }


    public StudentDetails save(StudentDetails studentDetails){
        return studentRepo.save(studentDetails);
    }
    
    public void deletebyId(int id) {
    	
		 studentRepo.deleteById(id);
    }
    
    public List<StudentDetails> findAll() {
		return studentRepo.findAll();
	}
    
    public Optional<StudentDetails> findById(int id){
    	return studentRepo.findById(id);
    }

   public Page<StudentDetails> findAllwithpagesize(int offset, int pagesize) {
        return studentRepo.findAll(PageRequest.of(offset,pagesize));
    }


    public  Page<StudentDetails> findbyemail(String keyword, Pageable pageable){
        return studentRepo.findAllByEmailContainingIgnoreCase(keyword, pageable);
    }

    public  Page<StudentDetails> searchkeyword(String keyword, Pageable pageable){
        return studentRepo.findAllByfNameContainingIgnoreCase(keyword, pageable);
    }
    public  Page<StudentDetails> findbysemester(String keyword, Pageable pageable){
        return studentRepo.findStudentDetailsBySemesterContainingIgnoreCase(keyword, pageable);
    }

    public Page<StudentDetails> findbyemailfnamesemester(String fName,String email,  String Semester,Pageable pageable){
        return studentRepo.findStudentDetailsByfNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndSemesterContainingIgnoreCase(fName,email, Semester, pageable);
    }
    public Page<StudentDetails> findbyemailfname(String fName,String email, Pageable pageable){
        return studentRepo.findStudentDetailsByfNameContainingIgnoreCaseAndEmailContainingIgnoreCase(fName,email,  pageable);
    }
    public Page<StudentDetails> findbyemailsemester(String email, String Semester,Pageable pageable){
        return studentRepo.findStudentDetailsByEmailContainingIgnoreCaseAndSemesterContainingIgnoreCase(email, Semester, pageable);
    }
    public Page<StudentDetails> findbyfnamesemester(String fName, String Semester,Pageable pageable){
        return studentRepo.findAllByfNameContainingIgnoreCaseAndSemesterContainingIgnoreCase(fName, Semester, pageable);
    }


    public StudentDetails findByEmail(String username) {
        return studentRepo.findByEmail(username);
    }
}
