package com.shamseddin.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a transaction involving a vehicle — either a purchase or a sale.
 * For PURCHASE transactions (dealer buying):
 * - buyer is null (dealer is buying)
 * - seller is the customer selling the car
 * - purchasePrice is set, salePrice is null
 * 
 * For SALE transactions (dealer selling):
 * - buyer is the customer buying the car
 * - seller is null (dealer is selling)
 * - salePrice is set, purchasePrice is null
 */
public class Transaction {
    private final Vehicle vehicle;
    private final Customer buyer;          // Null for purchase transactions
    private final Customer seller;         // Null for sale transactions
    private final TransactionType type;
    private final Admin processedBy;
    private final LocalDate transactionDate;
    private final BigDecimal purchasePrice; // Set only for purchase transactions
    private final BigDecimal salePrice;     // Set only for sale transactions
    private final VehicleStatus status;

    public Transaction(Vehicle vehicle, Customer buyer, Customer seller, TransactionType type,
                      Admin processedBy, BigDecimal price) {
        if (processedBy == null) {
            throw new IllegalArgumentException("Admin cannot be null");
        }
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        
        // Validate transaction type and related fields
        if (type == TransactionType.PURCHASE) {
            if (seller == null) {
                throw new IllegalArgumentException("Seller cannot be null for purchase transactions");
            }
            if (buyer != null) {
                throw new IllegalArgumentException("Buyer must be null for purchase transactions");
            }
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Purchase price must be greater than 0");
            }
        } else if (type == TransactionType.SALE) {
            if (buyer == null) {
                throw new IllegalArgumentException("Buyer cannot be null for sale transactions");
            }
            if (seller != null) {
                throw new IllegalArgumentException("Seller must be null for sale transactions");
            }
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Sale price must be greater than 0");
            }
        }

        this.vehicle = vehicle;
        this.buyer = buyer;
        this.seller = seller;
        this.type = type;
        this.processedBy = processedBy;
        this.transactionDate = LocalDate.now();
        
        // Set appropriate price based on transaction type
        if (type == TransactionType.PURCHASE) {
            this.purchasePrice = price;
            this.salePrice = null;
            this.status = VehicleStatus.AVAILABLE; // Dealer now owns the vehicle
        } else {
            this.purchasePrice = null;
            this.salePrice = price;
            this.status = VehicleStatus.SOLD; // Vehicle is sold to customer
        }
    }

    // Getters
    public Vehicle getVehicle() { return vehicle; }
    public Customer getBuyer() { return buyer; }
    public Customer getSeller() { return seller; }
    public TransactionType getType() { return type; }
    public Admin getProcessedBy() { return processedBy; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public BigDecimal getPurchasePrice() { return purchasePrice; }
    public BigDecimal getSalePrice() { return salePrice; }
    public VehicleStatus getStatus() { return status; }

    @Override
    public String toString() {
        return "Transaction{" +
                "vehicle=" + vehicle +
                ", type=" + type +
                ", buyer=" + buyer +
                ", seller=" + seller +
                ", processedBy=" + processedBy +
                ", transactionDate=" + transactionDate +
                ", price=" + (type == TransactionType.PURCHASE ? purchasePrice : salePrice) +
                ", status=" + status +
                '}';
    }
}
