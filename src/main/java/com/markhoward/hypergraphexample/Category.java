package com.markhoward.hypergraphexample;

public class Category {
    private String name;
    
    /**
     * Default constructor is needed by HypergraphDB to serialize.
     */
    public Category(){
        
    }
    
    public Category(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return String.format("Category: %s", getName());
    }
}
