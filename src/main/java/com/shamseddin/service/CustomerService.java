package com.shamseddin.service;


import com.shamseddin.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int id);
    Optional<Customer> getCustomerById(int id);
    List<Customer> getAllCustomers();
}
