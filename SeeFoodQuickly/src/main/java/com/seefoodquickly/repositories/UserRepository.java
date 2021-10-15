package com.seefoodquickly.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.seefoodquickly.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUserEmail(String email);

}
