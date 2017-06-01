package com.shop;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {

	Product findByName(String name);
	
	List<Product> findByIdIn(List<Long> ids);
}
