package com.shamseddin.model;

public class Address {
    private String street; //street address
    private String streetNumber;
    private String city;
    private String zip;
    private String country;

    public Address(String street,String streetNumber,String city, String zip, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.zip = zip;
        this.country = country;
    }

    public String getStreet() { return street; }
    public String getStreetNumber() { return streetNumber; }
    public String getCity() { return city; }
    public String getZip() { return zip; }
    public String getCountry() { return country; }
    

    public void setStreet(String street) { this.street = street; }
    public void setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; }
    public void setCity(String city) { this.city = city; }
    public void setZip(String zip) { this.zip = zip; }
    public void setCountry(String country) { this.country = country; }
    
    @Override
    public String toString() {
        return street + " " + streetNumber + "," + zip + " " + city + "," + country;
    }


}

