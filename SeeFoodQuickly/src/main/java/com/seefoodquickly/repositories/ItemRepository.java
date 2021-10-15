package com.seefoodquickly.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seefoodquickly.models.Item;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

}
