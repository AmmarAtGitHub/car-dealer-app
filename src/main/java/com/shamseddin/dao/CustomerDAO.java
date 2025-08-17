package com.shamseddin.dao;

import com.shamseddin.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    Customer addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomerById(int id);
    Optional<Customer> getCustomerById(int id);
    Optional<Customer> getCustomerByEmail(String email);
    List<Customer> getAllCustomers();
}