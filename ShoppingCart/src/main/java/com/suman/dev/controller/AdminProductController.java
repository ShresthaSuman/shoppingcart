package com.suman.dev.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.suman.dev.entity.Category;
import com.suman.dev.entity.Product;
import com.suman.dev.repository.AdminCategoryRepository;
import com.suman.dev.repository.AdminProductRepository;

@Controller
@RequestMapping("admin/product")
public class AdminProductController {

	String filePath = "/admin/product";
	@Autowired
	AdminProductRepository productRepository;

	@Autowired
	AdminCategoryRepository categoryRepository;

	@GetMapping
	public String index(Model model) {

		List<Category> catList = categoryRepository.findAll();
		Map<Integer, String> catMap = new HashMap<Integer, String>();
		for (Category cat : catList) {
			catMap.put(cat.getId(), cat.getName());
		}
		model.addAttribute("product", productRepository.findAll());
		model.addAttribute("catMap", catMap);
		return filePath + "/index";
	}

	@GetMapping("/add")
	public String add(@ModelAttribute Product product, Model model) {
		List<Category> category = categoryRepository.findAll();
		model.addAttribute("category", category);
		return filePath + "/addProduct";
	}

	@PostMapping("/addProduct")
	public String add(@Valid Product product, BindingResult result, Model model, RedirectAttributes attribute,
			MultipartFile file) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("category", categoryRepository.findAll());
			return filePath + "/addProduct";
		}
		boolean checkFile = false;
		byte[] bytes = file.getBytes();
		String imageName = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/media/" + imageName);
		if (imageName.endsWith("jpg") || imageName.endsWith("jpeg") || imageName.endsWith("png")) {
			checkFile = true;
		}
		attribute.addFlashAttribute("message", "Product successfully added");
		attribute.addFlashAttribute("alertClass", "alert-success");

		if (!checkFile) {
			attribute.addFlashAttribute("message", "File name must be jpg , jpeg or png");
			attribute.addFlashAttribute("alertClass", "alert-danger");
			attribute.addFlashAttribute("product", product);

		}

		String productName = product.getName();
		String slug = productName.toLowerCase().replace(" ", "-");
		Product productExist = productRepository.findBySlug(slug);
		if (productExist != null) {
			attribute.addFlashAttribute("message", "Product already exists");
			attribute.addFlashAttribute("alertClass", "alert-danger");
			attribute.addFlashAttribute("product", product);
		} else {
			product.setSlug(slug);
			product.setImage(imageName);
			Files.write(path, bytes);
			productRepository.save(product);
		}
		return "redirect:/admin/product/add";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id, Model model) {
		List<Category> catList = categoryRepository.findAll();
		Product product = productRepository.getOne(id);
		model.addAttribute("product", product);
		model.addAttribute("category", catList);
		return filePath + "/editProduct";
	}

	@PostMapping("/editProduct")
	public String editProduct(@Valid Product product, BindingResult result, Model model, RedirectAttributes attribute,
			MultipartFile file) throws IOException {
		
		Product currentProduct= productRepository.getOne(product.getId());
		if (result.hasErrors()) {
			model.addAttribute("product", currentProduct);
			model.addAttribute("category", categoryRepository.findAll());
			return filePath + "/editProduct";
		}
	
		boolean checkFile = false;
		byte[] bytes = file.getBytes();
		String imageName = file.getOriginalFilename();
		Path path = Paths.get("src/main/resources/static/media/" + imageName);
		
		if(!file.isEmpty()) {
			if (imageName.endsWith("jpg") || imageName.endsWith("jpeg") || imageName.endsWith("png")) {
				checkFile = true;
			}
		}else {
			checkFile= true;
		}
		
		
		attribute.addFlashAttribute("message", "Product successfully edited");
		attribute.addFlashAttribute("alertClass", "alert-success");

		if (!checkFile) {
			attribute.addFlashAttribute("message", "File name must be jpg , jpeg or png");
			attribute.addFlashAttribute("alertClass", "alert-danger");
			attribute.addFlashAttribute("product", product);

		}

		String productName = product.getName();
		String slug = productName.toLowerCase().replace(" ", "-");
		Product productExist = productRepository.findBySlugAndIdNot(slug,product.getId());
		if (productExist != null) {
			attribute.addFlashAttribute("message", "Product already exists");
			attribute.addFlashAttribute("alertClass", "alert-danger");
			attribute.addFlashAttribute("product", product);
		} else {
			product.setSlug(slug);
			
			if(!file.isEmpty()) {
				Path path2 = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
				Files.delete(path2);
				product.setImage(imageName);
				Files.write(path, bytes);
			}else {
				product.setImage(currentProduct.getImage());	
			}
			
			
			productRepository.save(product);
		}


		return "redirect:/admin/product/edit/"+product.getId();
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProduct(@PathVariable int id,RedirectAttributes attribute) {
		Product product = productRepository.getOne(id);
		Product currentProduct = productRepository.getOne(product.getId());
		
		Path path2 = Paths.get("src/main/resources/static/media/" + currentProduct.getImage());
		try {
			Files.delete(path2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		productRepository.deleteById(id);
		attribute.addFlashAttribute("message", "Deleted Successfully");
		attribute.addFlashAttribute("alertClass","alert-danger");
		
		return "redirect:/admin/product";
	}

}
