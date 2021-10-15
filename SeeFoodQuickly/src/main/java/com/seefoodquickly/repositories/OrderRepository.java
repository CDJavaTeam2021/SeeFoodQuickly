package com.seefoodquickly.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seefoodquickly.models.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}
