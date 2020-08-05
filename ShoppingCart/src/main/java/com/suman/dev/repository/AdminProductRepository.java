package com.suman.dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.suman.dev.entity.Product;

public interface AdminProductRepository extends JpaRepository<Product, Integer>{

}
