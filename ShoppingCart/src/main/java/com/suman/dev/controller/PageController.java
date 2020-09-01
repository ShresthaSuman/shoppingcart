package com.suman.dev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.suman.dev.entity.Page;
import com.suman.dev.repository.AdminPageRepository;

@Controller
@RequestMapping("/")
public class PageController {

	@Autowired
	private AdminPageRepository pageRepository;

	@GetMapping
	public String home(Model model) {
		Page page = pageRepository.findBySlug("home");
		String contents= page.getContents();

		model.addAttribute("page",page);
		return "page";
		
	}
	
	@GetMapping("/{slug}")
	public String otherPage(@PathVariable String slug , Model model) {
		Page page =pageRepository.findBySlug(slug);
		if(page == null) {
			return "redirect:/page";
		}
		model.addAttribute("page",page);
		return "page";
	}
}
