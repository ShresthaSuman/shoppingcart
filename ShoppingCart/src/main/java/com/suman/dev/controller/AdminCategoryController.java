package com.suman.dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.suman.dev.entity.Category;
import com.suman.dev.entity.Page;
import com.suman.dev.repository.AdminCategoryRepositiry;



@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

	String filepath= "/admin/category/";
	@Autowired
	AdminCategoryRepositiry categoryRepository;
	
	@GetMapping
	public String index(Model model) {
	List<Category>	category =categoryRepository.findAll();
	model.addAttribute("category",category );
		return   filepath+"index";
	}
	
	@GetMapping("/add")
	public String add(@ModelAttribute Category category) {
		return filepath+"addCategory";
	}
	@PostMapping("/addCategory")
	public String addCategory(@Valid Category category, BindingResult bindResult,Model model, RedirectAttributes reAttributes) {
		
		
		reAttributes.addFlashAttribute("message","Added successfully");
		reAttributes.addFlashAttribute("alertClass", "alert-success");
		String slug=category.getName().toLowerCase().replaceAll(" ", "-");
		Page checkCategoryExist = categoryRepository.findByName(category.getName());
		if(checkCategoryExist!=null) {
			reAttributes.addFlashAttribute("message","Category already exist, choose another different slug");
			reAttributes.addFlashAttribute("alertClass", "alert-danger");
			
		}else {
			category.setSlug(slug);
			category.setSorting(100);
			categoryRepository.save(category);
		}
		
		
		
		return filepath+"addCategory";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(Model model,@PathVariable int id) {
		model.addAttribute("category", categoryRepository.getOne(id));
		return filepath+"editCategory";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Category category,BindingResult binding,RedirectAttributes reAttributes) {
		
		reAttributes.addFlashAttribute("message","Edited successfully");
		reAttributes.addFlashAttribute("alertClass", "alert-success");
		String slug=category.getName().toLowerCase().replaceAll(" ", "-");
		Page checkCategoryExist = categoryRepository.findByName(category.getName());
		if(checkCategoryExist!=null) {
			reAttributes.addFlashAttribute("message","Category already exist, choose another different slug");
			reAttributes.addFlashAttribute("alertClass", "alert-danger");
			
		}else {
			category.setSlug(slug);
			
			categoryRepository.save(category);
		}
		
		return filepath+"editCategory";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id,RedirectAttributes reAttributes ) {
		categoryRepository.deleteById(id);
		reAttributes.addFlashAttribute("message","deleted successfully");
		reAttributes.addFlashAttribute("alertClass", "alert-success");
		return "redirect:/admin/category";
	}
	
}
