package com.example.AngularSpring.Repo;

import com.example.AngularSpring.Entity.StudentDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<StudentDetails, Integer> {

    Page<StudentDetails> findAllByEmailContainingIgnoreCase(String keyword, Pageable pageable);
    Page<StudentDetails> findAllByfNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<StudentDetails> findStudentDetailsBySemesterContainingIgnoreCase(String keyword, Pageable pageable);

}
