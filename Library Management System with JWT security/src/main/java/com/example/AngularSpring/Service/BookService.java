package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.Book;
import com.example.AngularSpring.Repo.BookRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    final BookRepo bookRepo;
    public BookService(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book save(Book book){
        return bookRepo.save(book);
    }
    public void deletebyid(String id){
        bookRepo.deleteById(id);
    }
    public List<Book> findAll(){
        return bookRepo.findAll();
    }
    public Optional<Book> findById(String id){
       return bookRepo.findById(id);
    }

    public List<Book> findbystdid(int id){
        return  bookRepo.findAllByStudentDetails_id(id);
    }

    public List<Book> findbystatus(int id){ return bookRepo.findAllByStatus(id);}
}
