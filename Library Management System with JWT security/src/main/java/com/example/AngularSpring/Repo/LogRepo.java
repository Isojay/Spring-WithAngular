package com.example.AngularSpring.Repo;

import com.example.AngularSpring.Entity.Book;
import com.example.AngularSpring.Entity.Log_Table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepo extends JpaRepository<Log_Table,Integer> {

    List<Log_Table> findAllByStudentDetails_id(int id);

}
