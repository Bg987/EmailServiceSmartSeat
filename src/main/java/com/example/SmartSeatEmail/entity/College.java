package com.example.SmartSeatEmail.entity;

import com.example.SmartSeatEmail.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "colleges") // Best practice: use plural table names
@Data
@NoArgsConstructor
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"college_Id\"")
    private Long collegeId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT") // Allows longer addresses
    private String address;

    @OneToOne
    @JoinColumn(name = "userid") // for join perpose for contact and mobile number
    private User user;
}
