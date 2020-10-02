package com.suman.dev.controller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.suman.dev.entity.Cart;
import com.suman.dev.entity.Product;
import com.suman.dev.repository.AdminProductRepository;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	AdminProductRepository productRepo;

	@GetMapping("/add/{id}")
	public String getProduct(@PathVariable int id, HttpSession session, Model model) {
		Product product = productRepo.getOne(id);
		if (session.getAttribute("cart") == null) {
			HashMap<Integer, Cart> cart = new HashMap<Integer, Cart>();
			cart.put(id, new Cart(id, product.getName(), 1, product.getPrice(), product.getImage()));
			session.setAttribute("cart", cart);

		} else {
			@SuppressWarnings({ "unchecked", "unused" })
			HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
			if (cart.containsKey(id)) {
				int qty = cart.get(id).getQuantity();
				cart.put(id, new Cart(id, product.getName(), qty++, product.getPrice(), product.getImage()));
			} else {

				cart.put(id, new Cart(id, product.getName(), 1, product.getPrice(), product.getImage()));
				session.setAttribute("cart", cart);
			}
		}

		HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
		int size = 0;
		double totalPrice = 0;
		for (Cart value : cart.values()) {
			size += value.getQuantity();
			totalPrice += value.getQuantity() * Double.parseDouble(value.getPrice());

		}

		model.addAttribute("size", size);
		model.addAttribute("totalPrice", totalPrice);

		return "cart_page";
	}
}
