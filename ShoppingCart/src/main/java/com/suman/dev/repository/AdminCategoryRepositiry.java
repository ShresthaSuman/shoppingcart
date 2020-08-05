package com.suman.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suman.dev.entity.Category;
import com.suman.dev.entity.Page;

public interface AdminCategoryRepositiry extends JpaRepository<Category, Integer> {

	Page findByName(String name);
	

}
