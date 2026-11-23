package com.mycompany.hotelapp;

public class K2703411_InternationalGuest extends K2703411_Guest {

    private final String passportNumber;
    
    //constructor 
    public K2703411_InternationalGuest(String guestId, String passportNumber, String fullName,
                              String contactNumber, String email) {
        super(guestId, fullName, contactNumber, email);
        
        if (passportNumber == null || passportNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Passport number cannot be empty.");
        }
        
        this.passportNumber = passportNumber.trim();
    }

    @Override
    public String getIdentificationNumber() {
        return passportNumber;
    }

    @Override
    public String getGuestType() {
        return "International";
    }
}
