package com.suman.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suman.dev.entity.Category;
import com.suman.dev.entity.Product;
import com.suman.dev.repository.AdminCategoryRepository;
import com.suman.dev.repository.AdminPageRepository;
import com.suman.dev.repository.AdminProductRepository;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	AdminCategoryRepository categoryRepository;
	@Autowired
	AdminProductRepository productRepository;

	@GetMapping("/{slug}")
	public String displayProduct(@PathVariable String slug, Model model) {
		if (slug.equalsIgnoreCase("all")) {
			List<Product> product = productRepository.findAll();
			model.addAttribute("product", product);
		} else {
			Category category= categoryRepository.findBySlug(slug);
			if(category == null) {
				return "redirect:/";
			}
			
			int catId= category.getId();
			String name = category.getName();
			List<Product> catProduct = productRepository.findAllByCategoryId(Integer.toString(catId));
			model.addAttribute("product",catProduct);
			model.addAttribute("categoryName", name);
		}

		return "product";
	}

}
