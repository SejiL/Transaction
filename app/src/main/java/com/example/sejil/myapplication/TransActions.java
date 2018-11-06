package com.example.sejil.myapplication;

public class TransActions {
    public String price;
    public String details;
    public String transactiontime;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTransactiontime() {
        return transactiontime;
    }

    public void setTransactiontime(String transactiontime) {
        this.transactiontime = transactiontime;
    }

    public TransActions(String price, String details, String transactiontime){
        this.price = price;
        this.details = details;
        this.transactiontime = transactiontime;
    }
}
