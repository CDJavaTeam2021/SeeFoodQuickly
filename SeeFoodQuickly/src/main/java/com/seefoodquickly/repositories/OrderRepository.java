package com.seefoodquickly.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seefoodquickly.models.Order;
import com.seefoodquickly.models.User;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	
	List<Order> findByOrderOpenIsOrderByCreatedAt(Boolean openState);
	
	List<Order> findAllByOrderByCreatedAt();
	
	List<Order> findByCustomerOrderByCreatedAt(User user);

}
