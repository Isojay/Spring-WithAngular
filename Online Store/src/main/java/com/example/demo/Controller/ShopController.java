package com.example.demo.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.dao.Category;

@Controller
public class ShopController {
	
	ProductService productService;
	CategoryService categoryService;
	
	public ShopController(ProductService theproductService,CategoryService thecategoryService) {
		
		productService = theproductService;
		categoryService = thecategoryService;
	}
	@GetMapping("/shop")
	public String Showshop(Model theModel) {
		
		List<Category> thecaCategories = categoryService.findAll();
		
		theModel.addAttribute("products", productService.findAll());
		theModel.addAttribute("categories", thecaCategories);
		
		return "shop";
	}
	@GetMapping("/shop/category/{id}")
	public String viewbycatProduct(@PathVariable int id, Model theModel) {
		
		List<Category> thecaCategories = categoryService.findAll();
		
		theModel.addAttribute("products",productService.getAllProductsByCategoryId(id));
		theModel.addAttribute("categories", thecaCategories);
		
		return "shop";
	}
	
	@GetMapping("/shop/viewproduct/{id}")
	public String viewProduct(@PathVariable int id, Model theModel) {
		
		theModel.addAttribute("product",productService.findById(id).get());
		
		return "viewProduct";
	}
	
	
	
}
