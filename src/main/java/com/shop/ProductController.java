package com.shop;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;


@RestController
public class ProductController {
	@Autowired
	private ProductRepository productRepository;

	@PostMapping(path="/product") 
	public ResponseEntity<String> addNewProduct (@RequestParam String name, @RequestParam double price, @RequestParam int quantity) throws Exception {
		Product n = new Product();
		n.setName(name);
		n.setPrice(price);
		n.setQuantity(quantity);
		productRepository.save(n);
		
		ResponseEntity<String> re = new ResponseEntity<>("Created",  HttpStatus.CREATED);
		return re;
	}

    @PutMapping(path = "/product")
    public ResponseEntity updateProduct(@RequestBody List<Product> product) throws Exception {
    	productRepository.save(product);
		ResponseEntity<String> re = new ResponseEntity<>("OK",  HttpStatus.OK);
		return re;
    }

	@GetMapping(path="/product/{productId}")
	public ResponseEntity<Iterable<?>>  getProduct(@PathVariable String productId) throws Exception  {
		return new ResponseEntity(productRepository.findOne(productId),  HttpStatus.OK);
	}

	@GetMapping(path="/product")
	public ResponseEntity  getAllProducts() throws Exception  {
		return new ResponseEntity<Iterable<?>>(productRepository.findAll(),  HttpStatus.OK);
	}

	@GetMapping(path="product/inventory", params="ids") 
	public ResponseEntity checkInventory(@RequestParam List<Long> ids) throws Exception {
		return
		Optional
		.ofNullable(productRepository.findByIdIn(ids))
		.map(result -> new ResponseEntity<>(result,  HttpStatus.OK))
		.orElse(new ResponseEntity<>( HttpStatus.NOT_FOUND));
	}
}