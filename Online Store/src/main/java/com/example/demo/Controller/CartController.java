package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dao.CartM;
import com.example.demo.dao.Product;
import com.example.demo.service.ProductService;

@Controller
public class CartController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/cart")
	public String showcart(Model model) {
		
		model.addAttribute("cart",CartM.cart);
		model.addAttribute("cartcount",CartM.cart.size());
		model.addAttribute("total", CartM.cart.stream().mapToDouble(Product::getPrice).sum());
		
		return "cart";
	}
	@GetMapping("/addToCart/{id}")
	public String addtoCart(@PathVariable int id) {
		
		CartM.cart.add(productService.findById(id).get());
		
		return "redirect:/shop";		
	}
	@GetMapping("/cart/removeItem/{index}")
	public String itemRemove(@PathVariable int index) {
		CartM.cart.remove(index);
		return "redirect:/index";
	}
	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("total", CartM.cart.stream().mapToDouble(Product::getPrice).sum());
		return "checkout";
	}
}
