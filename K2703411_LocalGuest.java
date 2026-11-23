package com.mycompany.hotelapp;

public class K2703411_LocalGuest extends K2703411_Guest {
    private final String nic;
    
    //constructor
    public K2703411_LocalGuest(String guestId, String nic, String fullName, String contactNumber, String email) {
        super(guestId, fullName, contactNumber, email);
        if (nic == null || nic.trim().isEmpty()) {
            throw new IllegalArgumentException("NIC cannot be empty.");
        }
        this.nic = nic.trim();
    }
    
    @Override
    public String getIdentificationNumber() {
        return nic;
    }
    
    @Override
    public String getGuestType() {
        return "Local";
    }
}


