package com.example.waba;

public class Profilead {
    private String email;
    private String Phonenumber;
    private String home;
    private String name;

    public Profilead() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return Phonenumber;
    }

    public void setMobile(String mobile) {
        this.Phonenumber = mobile;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Profilead(String email, String mobile, String home, String name) {
        this.email = email;
        this.Phonenumber = mobile;
        this.home = home;
        this.name = name;

    }
}
