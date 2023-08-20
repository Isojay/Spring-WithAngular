package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.service.CategoryService;
import com.example.demo.dao.Category;

@Controller

public class CategoriesController {
	
	CategoryService categoryService;
	
	
	public CategoriesController(CategoryService thecategoryService) {
		super();
		categoryService = thecategoryService;
	}
	
	@GetMapping("/admin")
	public String showHome() {
		return "adminHome";
	}
	@GetMapping("admin/categories")
	public String showCategories(Model theModel) {
		
		List<Category> theCategories = categoryService.findAll();
		theModel.addAttribute("categories", theCategories);
		
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String addCategories(Model theModel) {
		
		Category categories = new Category();
		
		theModel.addAttribute("category",categories);
		
		return "categoriesAdd";
	}
	
	@PostMapping("/admin/categories/add")
	public String saveCategories(@ModelAttribute("category") Category category ) {
		
		categoryService.save(category);
		
		return "redirect:/admin/categories";
	}
	@GetMapping("/admin/categories/delete/{id}")
	public String delcategories(@PathVariable int id) {
		
		categoryService.deleteById(id);
		
		return "redirect:/admin/categories";
	}
	@GetMapping("/admin/categories/update/{id}")
	public String updatecategories(@PathVariable int id,Model theModel) {
		
		Optional<Category> category = categoryService.findById(id);
		if(category.isPresent()) {
			
			theModel.addAttribute("category",category.get());
			return "categoriesAdd";
			
		}else {
			return "404";
		}
				
	}
	
}
