package com.shamseddin.service;

import com.shamseddin.dao.CustomerDAO;
import com.shamseddin.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerDAO dao;

    public CustomerServiceImpl(CustomerDAO dao) { this.dao = dao; }

    @Override
    public Customer addCustomer(Customer customer) {
        // basic validation
        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new IllegalArgumentException("email required");
        }
        // optionally check duplicate by email
        Optional<Customer> existing = dao.getCustomerByEmail(customer.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("customer with email already exists");
        }
        Customer created = dao.addCustomer(customer);
        logger.info("Created customer id {}", created.getId());
        return created;
    }

    @Override
    public void updateCustomer(Customer customer) {
        if (customer.getId() <= 0) throw new IllegalArgumentException("invalid customer id");
        dao.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(int id) {
        dao.deleteCustomerById(id);
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {
        return dao.getCustomerById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return dao.getAllCustomers();
    }
}
