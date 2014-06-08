package com.markhoward.hypergraphexample;

public class Product {
    private String name;
    private String specs;
    private double price;
    
    /**
     * Default constructor is needed by HypergraphDB to serialize.
     */
    public Product(){
        
    }
    
    public Product(String name, String specs, double price){
        this.name = name;
        this.specs = specs;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    @Override
    public String toString(){
        return String.format("Product name: %s, product specs: %s, Product price: %.2f", getName(), getSpecs(), getPrice());
    }
}
