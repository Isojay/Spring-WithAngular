package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Repo.StudentRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
	
	@Autowired
   StudentRepo studentRepo;


    public StudentDetails save(StudentDetails studentDetails){
        return studentRepo.save(studentDetails);
        
    }
    
    public void savestd(StudentDetails studentDetails) {
    	studentRepo.save(studentDetails);
    }
    
    public void deletebyId(int id) {
    	
		 studentRepo.deleteById(id);;
    }
    
    public List<StudentDetails> findAll() {
		return studentRepo.findAll();
	}
    
    public Optional<StudentDetails> findById(int id){
    	return studentRepo.findById(id);
    }

    public List<StudentDetails> findAll(String field) {
        return studentRepo.findAll(Sort.by(Sort.Direction.ASC,field));
    }
}
