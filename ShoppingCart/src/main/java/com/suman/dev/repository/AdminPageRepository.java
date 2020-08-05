package com.suman.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.suman.dev.entity.Page;

public interface AdminPageRepository  extends JpaRepository<Page, Integer>{
	 Page findBySlug(String slug);
	 
	 @Query("SELECT p FROM Page p where p.id != :id AND p.slug = :slug")
	 Page findBySlug(int id, String slug);
	
	 
	 
}
