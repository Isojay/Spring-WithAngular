package com.example.demo.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

	List<Product> findAllByCategory_Id(int id);



}
