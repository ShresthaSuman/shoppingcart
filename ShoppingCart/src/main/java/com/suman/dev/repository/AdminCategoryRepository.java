package com.suman.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suman.dev.entity.Category;
import com.suman.dev.entity.Page;

public interface AdminCategoryRepository extends JpaRepository<Category, Integer> {

	Page findByName(String name);
	Category findBySlug(String slug);
	

}
