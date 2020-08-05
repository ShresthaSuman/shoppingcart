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

import com.suman.dev.entity.Page;
import com.suman.dev.repository.AdminPageRepository;

@Controller
@RequestMapping("/admin/pages")
public class AdminPageController{
	
	@Autowired
	AdminPageRepository pageRepository;
	

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
	
	@PostMapping("/addPage")
	public String addPage(@Valid Page page, BindingResult bindResult,Model model, RedirectAttributes reAttributes) {
		if(bindResult.hasErrors() ) {
			return "/admin/page/addPage";
		}
		
		reAttributes.addFlashAttribute("message","Added successfully");
		reAttributes.addFlashAttribute("alertClass", "alert-success");
		String slug= page.getSlug() == "" ?page.getTitle().toLowerCase().replace(" ", "-")	
										  :page.getSlug().toLowerCase().replace(" ","-");
		Page checkSlug = pageRepository.findBySlug(slug);
		if(checkSlug!=null) {
			reAttributes.addFlashAttribute("message","Slug already exist, choose another different slug");
			reAttributes.addFlashAttribute("alertClass", "alert-danger");
			reAttributes.addFlashAttribute("page",page);
		}else {
			page.setSlug(slug);
			page.setSorting(100);
			pageRepository.save(page);
		}
		
		
		
		return "/admin/page/addPage";
	}
	
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		Page page=pageRepository.getOne(id);
		model.addAttribute("page",page);
		return "/admin/page/editPage";
	}
	
	@PostMapping("/edit")
	public String edit(@Valid Page page, BindingResult bindResult, Model model, RedirectAttributes reAttributes  ) {
		
		reAttributes.addFlashAttribute("message","page edited successfully");
		reAttributes.addFlashAttribute("alertClass", "alert-success");
		String slug= page.getSlug()== ""? page.getTitle().toLowerCase().replace(" ","-"):
					page.getSlug().toLowerCase().replace(" ", "-");
		
		Page checkSlug= pageRepository.findBySlug(page.getId(),slug);
		
		if(checkSlug!=null) {
			reAttributes.addFlashAttribute("message","Slug already exist, choose another different slug");
			reAttributes.addFlashAttribute("alertClass", "alert-danger");
			reAttributes.addFlashAttribute("page",page);
		}else {
			page.setSlug(slug);
			
			pageRepository.save(page);
		}
		return "/admin/page/editPage";	
		}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes reAttributes ) {
		pageRepository.deleteById(id);
		reAttributes.addFlashAttribute("message","deleted successfully");
		reAttributes.addFlashAttribute("alertClass", "alert-success");
		
		return "redirect:/admin/pages";
		
		
	}
	
}
