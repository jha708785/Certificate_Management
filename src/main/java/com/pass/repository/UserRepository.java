package com.pass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pass.entites.User;

public interface UserRepository extends JpaRepository<User, Long> {

	//check emai are exist or not..
	public boolean existsByEmail(String email);

	//find by email
	public User findByEmail(String email);

}
