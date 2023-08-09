package com.example.AngularSpring.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Queries")
public class Queries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Name")
    private String name;

    @NotEmpty
    @Column(name = "Email")
    private String email;

    @Column(name = "Phone_Number")
    private long contact;

    @Column(name = "Nachricht")
    private String message;

    @Column(name="Created Date")
    private LocalDate date;

    private int status; // 0 - new  1 - seen  2- solved

    private String querytopic;

}
