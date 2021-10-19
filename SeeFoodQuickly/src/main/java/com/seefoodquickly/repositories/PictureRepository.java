package com.seefoodquickly.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seefoodquickly.models.Product;
import com.seefoodquickly.models.Picture;

@Repository
public interface PictureRepository extends CrudRepository<Picture, Long> {
	
	List<Picture> findAllByProduct(Product product);
	
}
