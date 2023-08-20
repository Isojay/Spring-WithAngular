package com.example.demo.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.Users;

public interface UserRepo extends JpaRepository<Users, Integer> {

	Optional<Users> findUserByemail(String email);
}
