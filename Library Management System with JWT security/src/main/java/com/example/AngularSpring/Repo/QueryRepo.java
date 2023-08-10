package com.example.AngularSpring.Repo;

import com.example.AngularSpring.Entity.Queries;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryRepo extends JpaRepository<Queries, Integer> {

    List<Queries> findAllByStatus(int id);

}
