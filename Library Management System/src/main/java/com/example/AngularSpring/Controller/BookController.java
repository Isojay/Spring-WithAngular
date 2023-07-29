package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Entity.Book;
import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Service.BookService;
import com.example.AngularSpring.Service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/getDetails/{id}")
    public List<Book> findbyBookbystudentcode(@PathVariable int id){
        return bookService.findbystdid(id);
    }


    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book book){
        book.setStatus(0);

        return  bookService.save(book);
    }


    @PutMapping("/addBook")
    public Book editBook(@RequestBody Book book){

            if(book.getStatus() ==0 ){
                book.setStatus(1);
                book.setDate(LocalDate.now());
            }else {
                book.setStatus(0);
                book.setDate(null);
                book.setStudentDetails(null);
            }
            return bookService.save(book);
    }
    /*
     @PutMapping("/addBook")
    public ResponseEntity<?> editBook(@RequestBody Book book){
         if(book.getStudentDetails()!= null  && book.getStatus() == 0) {
             Optional<StudentDetails> present = studentService.findById(book.getStudentDetails().getId());
             if (present.isPresent()) {
                     book.setStatus(1);
                     book.setDate(LocalDate.now());
                 } else {
                     book.setStatus(0);
                     book.setDate(null);
                     book.setStudentDetails(null);
                 }
                 return ResponseEntity.ok(bookService.save(book));
         }else {
             String errorMessage = "Invalid StudentDetails ID";
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
         }

    }*/


}
