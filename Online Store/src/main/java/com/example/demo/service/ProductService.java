package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repo.ProductRepo;
import com.example.demo.dao.Product;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo productRepo;

	
	public void save(Product product) {		
		productRepo.save(product);
	}
	public void deleteById(int id) {
		productRepo.deleteById(id);
	}
	public List<Product> findAll() {
		return productRepo.findAll();
	}
	public Optional<Product> findById(int id) {
		return productRepo.findById(id);
	}
	public List<Product> getAllProductsByCategoryId(int id) {
		return productRepo.findAllByCategory_Id(id);
	}
}
