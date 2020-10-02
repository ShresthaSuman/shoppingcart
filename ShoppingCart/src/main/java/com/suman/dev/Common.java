package com.suman.dev;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.suman.dev.entity.Cart;
import com.suman.dev.entity.Category;
import com.suman.dev.entity.Page;
import com.suman.dev.repository.AdminCategoryRepository;
import com.suman.dev.repository.AdminPageRepository;

@SuppressWarnings("unchecked")
@ControllerAdvice
public class Common {

	@Autowired
	AdminPageRepository pageRepository ;
	
	@Autowired
	AdminCategoryRepository categoryRepository;
	
	@ModelAttribute
	public void commonSharedData(Model model, HttpSession session) {
		List<Page> page = pageRepository.findAll();
		List<Category> category= categoryRepository.findAll();
		
		boolean checkActiveCart = false;
		if(session.getAttribute("cart") != null ) {
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart"); 
			int size =0;
			double totalPrice=0;
			for (Cart value : cart.values()) {
				size += value.getQuantity();
				totalPrice += value.getQuantity() * Double.parseDouble( value.getPrice());
				
			}
			checkActiveCart = true;
			model.addAttribute("size",size);
			model.addAttribute("totalPrice",totalPrice);
			model.addAttribute("checkActiveCart", checkActiveCart);
		}
		
		model.addAttribute("commonPage",page);
		model.addAttribute("commonCategories", category);
		
		
	}
}
