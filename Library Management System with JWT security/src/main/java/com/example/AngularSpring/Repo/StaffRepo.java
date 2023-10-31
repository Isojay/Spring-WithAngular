package com.example.AngularSpring.Repo;

import com.example.AngularSpring.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepo extends JpaRepository<Staff,Integer> {

    Staff findBySemail(String email);


}
