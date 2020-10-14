package com.suman.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suman.dev.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	Admin findByUsername(String username);
}
