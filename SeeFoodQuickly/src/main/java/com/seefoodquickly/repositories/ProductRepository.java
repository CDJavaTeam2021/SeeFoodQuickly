package com.seefoodquickly.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seefoodquickly.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
