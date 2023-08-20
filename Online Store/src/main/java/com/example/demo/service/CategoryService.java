package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Repo.CategoryRepo;
import com.example.demo.dao.Category;

@Service
public class CategoryService  {

	@Autowired
	CategoryRepo categoryRepo;
	
	public void save(Category category) {		
		categoryRepo.save(category);
	}
	public void deleteById(int id) {
		categoryRepo.deleteById(id);
	}
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}
	public Optional<Category> findById(int id) {
		return categoryRepo.findById(id);
	}

}
