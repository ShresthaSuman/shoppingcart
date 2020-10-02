package com.suman.dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suman.dev.entity.Product;

public interface AdminProductRepository extends JpaRepository<Product, Integer>{

	Product findBySlug(String slug);

	Product findBySlugAndIdNot(String slug, int id);

	List<Product> findAllByCategoryId(String ccategoryId);

}
