package com.shamseddin.model;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Address address;
    private String bankAccount;

    
    public Customer(String firstName, String lastName, String email, String phone, Address address, String bankAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.bankAccount = bankAccount;
    }
    
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Address getAddress() { return address; }
    public String getBankAccount() { return bankAccount; }
    public String getFullName() { return firstName + " " + lastName; }
     
    @Override
    public String toString() {
        return "Customer: " + firstName + " " + lastName + ", Email: " + email + ", Phone: " + phone + ", Address: " + address + ", Bank Account: " + bankAccount   ;
    }
    
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }
    public void setAddress(Address address) { this.address = address; }
    
}