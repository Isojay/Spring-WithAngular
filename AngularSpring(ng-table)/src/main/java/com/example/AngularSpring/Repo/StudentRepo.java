package com.example.AngularSpring.Repo;

import com.example.AngularSpring.Entity.StudentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<StudentDetails, Integer> {

    Page<StudentDetails> findAllByEmailContainingIgnoreCase(String keyword, Pageable pageable);


    Page<StudentDetails> findAllByfNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<StudentDetails> findStudentDetailsBySemesterContainingIgnoreCase(String keyword, Pageable pageable);

    Page<StudentDetails> findAllBylNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<StudentDetails> findStudentDetailsByfNameContainingIgnoreCaseAndEmailContainingIgnoreCase(String fName,String email,  Pageable pageable);

    Page<StudentDetails> findAllByfNameContainingIgnoreCaseAndSemesterContainingIgnoreCase(String fName, String semester, Pageable pageable);

    Page<StudentDetails> findStudentDetailsByEmailContainingIgnoreCaseAndSemesterContainingIgnoreCase(String email, String semester, Pageable pageable);

    Page<StudentDetails> findStudentDetailsByfNameContainingIgnoreCaseAndEmailContainingIgnoreCaseAndSemesterContainingIgnoreCase(String fName,String email, String semester, Pageable pageable);





}
