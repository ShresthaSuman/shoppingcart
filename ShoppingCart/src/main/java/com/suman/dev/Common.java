package com.suman.dev;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.suman.dev.entity.Page;
import com.suman.dev.repository.AdminPageRepository;

@ControllerAdvice
public class Common {

	@Autowired
	AdminPageRepository pageRepository ;
	@ModelAttribute
	public void commonSharedData(Model model) {
		List<Page> page = pageRepository.findAll();
		model.addAttribute("commonPage",page);
	}
}
