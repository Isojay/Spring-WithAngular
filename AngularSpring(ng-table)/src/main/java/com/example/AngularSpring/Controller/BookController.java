package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Entity.Book;
import com.example.AngularSpring.Service.BookService;
import com.example.AngularSpring.Service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    final BookService bookService;
    final StudentService studentService;

    public BookController(BookService bookService, StudentService studentService) {
        this.bookService = bookService;
        this.studentService = studentService;
    }

    @GetMapping("/getBook")
    public List<Book> showBooks(){
        return bookService.findAll();
    }

    @GetMapping("/deleteBook/{id}")
    public void deleteBook(@PathVariable String id){
        bookService.deletebyid(id);
    }

    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book book){
        book.setStatus(0);

        return  bookService.save(book);
    }
    @PutMapping("/addBook")
    public Book editBook(@RequestBody Book book){
        Book book1 = bookService.findById(book.getBcode()).get();
        if(book1.getStatus() ==0 && book.getStudentDetails()!=null ){
            book.setStatus(1);
            book.setDate(LocalDate.now());
        }else {
            book.setStatus(0);
            book.setDate(null);
            book.setStudentDetails(null);
        }
        return bookService.save(book);
    }


}
