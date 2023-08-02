package com.example.AngularSpring.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="Books")
public class Book {

    @Id
    @Column(name="Book Code")
    private String bcode;

    @Column(name="Book Name")
    private String bname;

    @Column(name="Book Author")
    private  String bauthor;

    @Column(name="Borrowed Date")
    private LocalDate date;

    @Column(name="Status")
    private int status;

    @ManyToOne
    @JoinColumn(name="Student Id")
    private StudentDetails studentDetails;


    @Column(name="Publish Year")
    private int year;

    public String getBcode() {
        return bcode;
    }

    public void setBcode(String bcode) {
        this.bcode = bcode;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public StudentDetails getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(StudentDetails studentDetails) {
        this.studentDetails = studentDetails;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Book() {
    }


    public Book(String bcode, String bname, String bauthor, LocalDate date, int status, StudentDetails studentDetails, int year) {
        this.bcode = bcode;
        this.bname = bname;
        this.bauthor = bauthor;
        this.date = date;
        this.status = status;
        this.studentDetails = studentDetails;
        this.year = year;
    }


    @Override
    public String toString() {
        return "Book{" +
                "bcode='" + bcode + '\'' +
                ", bname='" + bname + '\'' +
                ", bauthor='" + bauthor + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", studentDetails=" + studentDetails +
                ", year=" + year +
                '}';
    }
}
