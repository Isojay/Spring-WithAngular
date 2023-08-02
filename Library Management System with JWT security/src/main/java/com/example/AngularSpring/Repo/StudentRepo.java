package com.example.AngularSpring.Repo;

import com.example.AngularSpring.Entity.Staff;
import com.example.AngularSpring.Entity.StudentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<StudentDetails, Integer> {

    StudentDetails findByEmail(String email);

    Page<StudentDetails> findAllByEmailContainingIgnoreCase(String keyword, Pageable pageable);


    Page<StudentDetails> findAllByfNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<StudentDetails> findStudentDetailsBySemesterContainingIgnoreCase(String keyword, Pageable pageable);

    Page<StudentDetails> findStudentDetailsByfNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String fName,String email,  Pageable pageable);

    Page<StudentDetails> findAllByfNameContainingIgnoreCaseAndSemesterContainingIgnoreCase(String fName, String semester, Pageable pageable);

    Page<StudentDetails> findStudentDetailsByEmailContainingIgnoreCaseAndSemesterContainingIgnoreCase(String email, String semester, Pageable pageable);

    Page<StudentDetails> findStudentDetailsByfNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndSemesterContainingIgnoreCase(String fName,String email, String semester, Pageable pageable);





}
