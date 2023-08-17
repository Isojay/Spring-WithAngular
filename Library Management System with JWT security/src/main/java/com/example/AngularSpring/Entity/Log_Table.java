package com.example.AngularSpring.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Log Table")
public class Log_Table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="Student Id")
    private StudentDetails studentDetails;

    @ManyToOne
    @JoinColumn(name="Staff Id")
    private Staff staff;

    @Column(name="Logged on")
    private LocalDateTime localDateTime;

    private String OS;

   private String browser;



}
