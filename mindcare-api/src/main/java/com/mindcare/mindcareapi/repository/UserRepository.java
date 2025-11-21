package com.mindcare.mindcareapi.repository;

import com.mindcare.mindcareapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
