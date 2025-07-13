package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data // Generates getters, setters, toString(), etc.
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all arguments
@Entity // Marks this class as a JPA entity
@Table(name = "users") // Specifies the table name in the database
public class UserEntity {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures the ID generation strategy
    private Long id;

    private String firstName;
    private String lastName;
    private String idCard;
    private LocalDate dateOfBirth;
    private String position;
    private String role;
    private LocalDate startDate;
    private LocalDate endDate;
    private String email;
    private String phoneNumber;
}