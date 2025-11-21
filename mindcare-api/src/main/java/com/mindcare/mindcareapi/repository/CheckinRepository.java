package com.mindcare.mindcareapi.repository;

import com.mindcare.mindcareapi.model.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckinRepository extends JpaRepository<Checkin, Long> {

    
    List<Checkin> findByUserId(Long userId);
}
