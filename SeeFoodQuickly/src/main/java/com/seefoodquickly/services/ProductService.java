package com.seefoodquickly.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.seefoodquickly.models.Product;
import com.seefoodquickly.repositories.ProductRepository;

@Service
public class ProductService {
	private ProductRepository pRepo;
	public ProductService(ProductRepository pRepo) {
		this.pRepo = pRepo;
	}
	
	//Create Product
	public Product createProduct(Product newProduct) {
		return this.pRepo.save(newProduct);
	}
	
	//Get All Products
	public List<Product> getAllProducts(){
		return this.pRepo.findAll();
	}

	//Find Product
	public Product findProduct (Long id) {
		return this.pRepo.findById(id).orElse(null);
	}
}
