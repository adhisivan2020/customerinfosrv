package com.example.customerinfo.controller;

import com.example.customerinfo.model.*;
import com.example.customerinfo.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class customercontroller {

	@Autowired
	customerrepository customerRepository;

	@GetMapping("/customers")
	public ResponseEntity<List<customer>> getAllcustomers(@RequestParam(required = false) String name) {
		try
		{
			List<customer> customers = new ArrayList<customer>();
			
			if (name == null)
					customerRepository.findAll().forEach(customers::add);
			else
					customerRepository.findByNameContaining(name).forEach(customers::add);
			
			if (customers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(customers, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/customers/active")
	public ResponseEntity<List<customer>> findByPublished() {
		try
		{
			List<customer> customers = customerRepository.findByActive(true);
			
			if (customers.isEmpty()) {
				return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(customers, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity<customer> getCustomerById(@PathVariable("id") long id) {
		Optional<customer> customerData = customerRepository.findById(id);
		
		if (customerData.isPresent()) {
			return new ResponseEntity<>(customerData.get(), HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/customers")
	public ResponseEntity<customer> createCustomer(@RequestBody customer cust) {
		try
		{
			customer _customer = customerRepository.save(new customer(cust.getName(),cust.getAddress(),cust.getPhone(),cust.isActive()));
			return new ResponseEntity<>(_customer, HttpStatus.CREATED);
			
		} catch (Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PostMapping("/customers/multiple")
	public ResponseEntity<List<customer>> createCustomer(@RequestBody List<customer> custs) {
		try
		{
			List<customer> customers = new ArrayList<customer>();
			
			custs.forEach(c -> {
				customer _customer = customerRepository.save(new customer(c.getName(),c.getAddress(),c.getPhone(),c.isActive()));
				customers.add(_customer);
			});
			
			return new ResponseEntity<>(customers, HttpStatus.CREATED);
			
		} catch (Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
		
	
	
	@PutMapping("/customers/{id}")
	public ResponseEntity<customer> updateCustomer(@PathVariable("id") long id, @RequestBody customer cust) {
		Optional<customer> customerData = customerRepository.findById(id);
		
		if (customerData.isPresent()) {
			customer _customer = customerData.get();
			_customer.setName(cust.getName());
			_customer.setAddress(cust.getAddress());
			_customer.setPhone(cust.getPhone());
			_customer.setActive(cust.isActive());
			
			return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
		try {
			customerRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@DeleteMapping("/customers")
	public ResponseEntity<HttpStatus> deleteAllCustomers() {
		try {
			customerRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e)
		{
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

}
