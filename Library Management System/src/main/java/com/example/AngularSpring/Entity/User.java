package com.example.AngularSpring.Entity;


import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "Staff Name")
    private String sname;

    @Column(name= "Staff Email")
    private String semail;

    @Column(name = "Password")
    private String spassword;
}
