package com.suman.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.suman.dev.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUsername(String username);

}
