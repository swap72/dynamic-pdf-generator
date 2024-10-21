package com.example.pdfgenerator.model;

import java.util.List;
import java.util.Objects;

public class InvoiceRequest {

    private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress;
    private List<Item> items;

    // Getters and Setters for the InvoiceRequest class fields
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSellerGstin() {
        return sellerGstin;
    }

    public void setSellerGstin(String sellerGstin) {
        this.sellerGstin = sellerGstin;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyerGstin() {
        return buyerGstin;
    }

    public void setBuyerGstin(String buyerGstin) {
        this.buyerGstin = buyerGstin;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    // Inner static class Item with appropriate fields, getters, and setters
    public static class Item {
        private String name;
        private String quantity;
        private double rate;
        private double amount;

        // Getters and Setters for Item class
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        // Override toString method for better debugging/logging
        @Override
        public String toString() {
            return String.format("Name: %s, Quantity: %s, Rate: %.2f, Amount: %.2f",
                    name, quantity, rate, amount);
        }

        // Optional: Equals and hashCode for comparison purposes
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return Double.compare(item.rate, rate) == 0 &&
                    Double.compare(item.amount, amount) == 0 &&
                    Objects.equals(name, item.name) &&
                    Objects.equals(quantity, item.quantity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, quantity, rate, amount);
        }
    }
}
