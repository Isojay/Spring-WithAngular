package com.example.AngularSpring.Repo;

import com.example.AngularSpring.Entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<StudentDetails, Integer> {

}
