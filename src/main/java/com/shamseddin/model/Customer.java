package com.shamseddin.model;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String bankAccount;
    // Address fields
    private String street;
    private String zipCode;
    private String city;
    private String country;


    /**
     * Constructor to create a new vehicle with basic required information.
     */
    public Customer(String firstName, String lastName, String email, String phone,String bankAccount, String street, String city, String zipCode , String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.bankAccount = bankAccount;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    public Customer(int id, String firstName, String lastName, String email, String phone, String bankAccount, String street, String city, String zipCode , String country) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.bankAccount = bankAccount;
        this.street = street;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;


    }
    // no-args constructor
    public Customer(){}

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getStreet() { return street; }
    public String getZipCode() { return zipCode; }
    public String getCity() { return city; }
    public String getCountry() { return country; }
    public String getBankAccount() { return bankAccount; }
    public String getFullName() { return firstName + " " + lastName; }

    @Override
    public String toString() {
        return "Customer: " + firstName + " " + lastName + ", Email: " + email + ", Phone: " + phone +  "Street, Zip, City: "+ street +" " +zipCode +" "+ city  ;
    }

    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }
    public void setStreet(String street) { this.street = street; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public void setCity(String city) { this.city = city; }
    public void setCountry(String country) { this.country = country; }

    public void setFullName(String fullName) {
        String[] names = fullName.split(" ");
        if (names.length == 2) {
            this.firstName = names[0];
            this.lastName = names[1];
        } else {
            throw new IllegalArgumentException("Invalid full name: " + fullName);
        }

    }
    
}
