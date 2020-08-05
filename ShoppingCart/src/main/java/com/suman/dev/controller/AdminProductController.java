package com.suman.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suman.dev.repository.AdminProductRepository;

@Controller
@RequestMapping("admin/product")
public class AdminProductController {

	String filePath="/admin/product";
	@Autowired
	AdminProductRepository produsctRepository;
	
	@GetMapping
	public String index() {
		
		return filePath+"/index";	
		}
}
