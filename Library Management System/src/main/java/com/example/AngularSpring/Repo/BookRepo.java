package com.example.AngularSpring.Repo;

import com.example.AngularSpring.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book, String> {

    List<Book> findAllByStudentDetails_id(int id);
    List<Book> findAllByStatus(int id);


}
