package com.example.demo.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.dao.Category;
import com.example.demo.dao.Product;
import com.example.demo.dao.ProductDTO;

@Controller
public class ProductsController {
	
	public static String Uploaddir =  System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	
	ProductService productService;
	CategoryService categoryService;
	
	public ProductsController(ProductService theproductService,CategoryService thecategoryService) {
		
		productService = theproductService;
		categoryService = thecategoryService;
	}
	
	@GetMapping("admin/products")
	public String showproducts(Model theModel) {
		
		List<Product> theCategories = productService.findAll();
		theModel.addAttribute("products", theCategories);
		
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String addCategories(Model theModel) {
		
		ProductDTO products = new ProductDTO();
		List<Category> theCategories = categoryService.findAll();
		theModel.addAttribute("categories",theCategories);
		theModel.addAttribute("productDTO",products);
		
		
		
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String saveCategories(@ModelAttribute("productDTO") ProductDTO productDTO,
									@RequestParam("productImage") MultipartFile file,
										@RequestParam("imgName") String imgName) throws IOException {
		
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.findById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID;
		if(!file.isEmpty()) {
			imageUUID = file.getOriginalFilename();
			Path filenameandPath = Paths.get(Uploaddir, imageUUID);
			Files.write(filenameandPath, file.getBytes());
		}else {
			imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		
		
		productService.save(product);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String delproducts(@PathVariable int id) {
		
		productService.deleteById(id);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/update/{id}")
	public String updateproducts(@PathVariable int id,Model theModel) {
		
		Product product = productService.findById(id).get();
		ProductDTO productDTO = new ProductDTO() ;
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		
			List<Category> theCategories = categoryService.findAll();
			theModel.addAttribute("categories",theCategories);
			theModel.addAttribute("productDTO",productDTO);
			return "productsAdd";
	
				
	}
	
	
	
}
