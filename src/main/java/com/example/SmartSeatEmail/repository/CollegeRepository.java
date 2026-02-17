package com.example.SmartSeatEmail.repository;

import com.example.SmartSeatEmail.entity.College;
import com.example.SmartSeatEmail.DTO.collegeDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College, Long> {

    @Query("SELECT new com.example.SmartSeatEmail.DTO.collegeDetailsDTO(" +
            "c.user.mail, c.user.mobileNumber, c.name, c.address) " +
            "FROM College c WHERE c.collegeId = :collegeId")
    Optional<collegeDetailsDTO> findCollegeDetailsById(@Param("collegeId") Long collegeId);
}
