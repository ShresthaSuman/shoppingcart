package com.suman.dev.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suman.dev.entity.Page;
import com.suman.dev.repository.AdminPageRepository;

@Controller
@RequestMapping("/admin/pages")
public class AdminPageController{
	
	AdminPageRepository pageRepository;
	public AdminPageController(AdminPageRepository pageRepository) {
		// TODO Auto-generated constructor stub
		this.pageRepository=pageRepository;
	}
	

	@GetMapping
	public String index(Model model) {
	List<Page> pages=	pageRepository.findAll();
	model.addAttribute("pages",pages);
				return "admin/page/index";
	}
	@GetMapping("/addPage")
	public String addPage(@ModelAttribute Page page) {
		return "admin/page/addPage";
		
	}
}
