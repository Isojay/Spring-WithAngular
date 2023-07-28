package com.example.AngularSpring.Entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Entity
@Table(name = "Student")
public class StudentDetails {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(name = "First_name")
    private String fName;

    @NotEmpty
    @Column(name = "Last_name")
    private String lName;

    @Column(nullable = false, unique = true)
    @NotEmpty
    private String email;

    private String semester;
    private String password;



    public StudentDetails() {
    }
    public StudentDetails(int id) {
        this.id = id;
    }
    public StudentDetails(int id, String fName, String lName, String email, String semester, String password) {
        this.id = id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.semester = semester;
        this.password = password;
    }



    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty
    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getId() {
        return id;
    }

    public String getSemester() {
        return semester;
    }

    @NotEmpty
    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @NotEmpty
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "StudentDetails{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", email='" + email + '\'' +
                ", semester='" + semester + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
