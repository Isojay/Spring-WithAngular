package com.example.AngularSpring.Repo;

import com.example.AngularSpring.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepo extends JpaRepository<Book, String> {



}
