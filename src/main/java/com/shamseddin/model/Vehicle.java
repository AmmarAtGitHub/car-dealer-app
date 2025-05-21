package com.shamseddin.model;

/**
 * Represents a vehicle with various attributes.
 */
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;




public class Vehicle {
    private  int id;
    private final String vin;
    private final String brand;
    private final String model;
    private final int year;
    private int mileage;
    private String color;
    private BigDecimal askingPrice;
    private List<Photo> photos;
    private List<Document> documents;
    private VehicleStatus status; // Available, Reserved, Sold, In Repair
    private VehicleCondition condition; // NEW, USED, CERTIFIED_PRE_OWNED, DAMAGED
    private List<Transaction> transactionHistory;


    public Vehicle(String vin, String brand, String model, int year, int mileage, String color) {
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.status = VehicleStatus.AVAILABLE;
        this.condition = VehicleCondition.USED;
        this.askingPrice = null;

        this.photos = new ArrayList<>();
        this.documents = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
    }


    public Vehicle(int id, String vin, String brand, String model, int year,int mileage, String color) {
        this.id = id;
        this.vin = vin;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.askingPrice = null;
        this.status = VehicleStatus.AVAILABLE;

        this.photos = new ArrayList<>();
        this.documents = new ArrayList<>();
        this.transactionHistory = new ArrayList<>();
        
    }

  

    // Getters for all fields
    public int getId() { return id; }
    public String getVin() { return vin; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public int getMileage() { return mileage; }
    public String getColor() { return color; }
    public BigDecimal getAskingPrice() { return askingPrice; }
    public VehicleStatus getStatus() { return status; }
    // Setters only for mutable fields
    public void setColor(String color) { this.color = color; }
    public void setMileage(int mileage) { this.mileage = mileage; }
    public void setAskingPrice(BigDecimal askingPrice) {
        if (askingPrice == null || askingPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Asking price must be greater than 0.");
        }
        this.askingPrice = askingPrice;
    }



    public List<Photo> getPhotos() { return photos; }
    public List<Document> getDocuments() { return documents; }

    public void addPhoto(Photo photo) { photos.add(photo); }
   

    public void addDocument(Document document) {documents.add(document);}

    public void removePhoto(Photo photo) { photos.remove(photo); }
    public void removeDocument(Document document) { documents.remove(document); }

    public void clearPhotos() { photos.clear(); }
    public void clearDocuments() { documents.clear(); }

    public void markAsSold(Transaction transaction){
        if (transaction == null || transaction.getType() != TransactionType.SALE){
            throw new IllegalArgumentException("Transaction must be a SALE.");
        }
        
        this.status = VehicleStatus.SOLD;
        this.transactionHistory.add(transaction);
        }

    public void markAsAvailable(Transaction transaction){
        if (transaction == null || transaction.getType() != TransactionType.PURCHASE){
            throw new IllegalArgumentException("Transaction must be a PURCHASE.");
        }
        this.status = VehicleStatus.AVAILABLE;
        this.transactionHistory.add(transaction);
    }

  public void updatePrice(BigDecimal newPrice, Admin processedBy){
    if (processedBy == null ){
        throw new IllegalArgumentException("Admin cannot be null.");
    }
    if (newPrice.compareTo(BigDecimal.ZERO) <= 0){
        throw new IllegalArgumentException("Price must be greater than 0.");
    }
    if (newPrice.compareTo(this.askingPrice) > 0){
    this.askingPrice = newPrice;
  }
}
    
@Override
public String toString() {
    return "Vehicle{" +
            "id=" + id +
            ", vin='" + vin + '\'' +
            ", brand='" + brand + '\'' +
            ", model='" + model + '\'' +
            ", year=" + year +
            ", mileage=" + mileage +
            ", color='" + color + '\'' +
            ", askingPrice=" + askingPrice +
            ", status=" + status +
            ", transactionHistory=" + transactionHistory +
            '}';
        }


        

}
