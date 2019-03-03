package com.example.springdemo.repo;

import com.example.springdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rafik Gasparyan 02/03/2019
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}