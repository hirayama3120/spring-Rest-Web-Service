package com.example.controller;

import com.example.domain.Customer;
import com.example.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customers")
public class CustomerRestController {
    @Autowired
    CustomerService customerService;

    @GetMapping
    Page<Customer> getCustomers(@PageableDefault Pageable pageable) {
        Page<Customer> customers = customerService.findAll(pageable);
        return customers;
    }

    @GetMapping(path = "{id}")
    Optional<Customer> getCustomer(@PathVariable Integer id) {
        Optional<Customer> customer = customerService.findById(id);
        return customer;
    }

    @PostMapping
    ResponseEntity<Customer> postCustomers(@RequestBody Customer customer, UriComponentsBuilder uriBuilder) {
        Customer created = customerService.create(customer);
        URI location = uriBuilder.path("api/customers/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "{id}")
    Customer putCustomer(@PathVariable Integer id, @RequestBody Customer customer) {
        customer.setId(id);
        return customerService.update(customer);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCustomer(@PathVariable Integer id) {
        customerService.deleteById(id);
    }
}
