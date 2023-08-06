package com.example.AngularSpring.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Queries")
public class Queries {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty
    @Column(name = "Email")
    private String email;

    @Column(name = "Phone Number")
    private int contact;

    @Column(name = "Nachricht")
    private String message;


    private int status; // 0 - new  1 - seen  2- solved




}
