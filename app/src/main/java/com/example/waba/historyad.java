package com.example.waba;

import java.util.Date;

public class historyad {
    private String cash;
    private String date;
    private String driver;
    private String location;


    public historyad() {
    }

    public historyad(String cash, String date, String driver, String location) {
        this.cash = cash;
        this.date = date;
        this.driver = driver;
        this.location = location;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

