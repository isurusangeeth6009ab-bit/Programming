/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.hotelapp;

/**
 *
 * @author User
 */
public abstract class K2703411_Guest {
    private final String guestId;    //all the attributes are private
    private final String fullName;
    private String contactNumber;
    private String email;
    
    //constructor
    public K2703411_Guest(String guestId, String fullName, String contactNumber, String email) {
        this.guestId = guestId;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getGuestId() {   //setters and getter to access attributes 
        return guestId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setContactNumber(String contactNumber) {
        if (contactNumber == null || contactNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("contact number cannot be empty.");
        }
        this.contactNumber = contactNumber;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("email cannot be empty.");
        }
        this.email = email;
    }
    
    public abstract String getIdentificationNumber();
    public abstract String getGuestType();
}
