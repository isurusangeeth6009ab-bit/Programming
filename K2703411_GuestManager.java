package com.mycompany.hotelapp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class K2703411_GuestManager {

    private final Map<String, K2703411_Guest> guests = new LinkedHashMap<>();
    private int guestCounter = 0; // to generate the next guestId. 

    //register local guests
    public K2703411_Guest registerLocalGuest(String nic, String fullName, String contact, String email) {
        validateCommonDetails(fullName, contact, email);
        if (!K2703411_InputValidator.isValidNIC(nic)) {
            throw new IllegalArgumentException(
                    "Invalid NIC. Use 9 digits followed by V or X, or 12 digits.");
        }

        if (isIdDuplicate(nic)) {
            throw new IllegalArgumentException("A guest with this NIC is already registered.");
        }

        K2703411_Guest guest = new K2703411_LocalGuest(generateGuestId(), nic, fullName, contact, email);
        guests.put(guest.getGuestId(), guest);
        return guest;
    }

    //register international guests
    public K2703411_Guest registerInternationalGuest(
            String passport,
            String fullName,
            String contact,
            String email) {

        validateCommonDetails(fullName, contact, email);

        if (!K2703411_InputValidator.isValidPassport(passport)) {
            throw new IllegalArgumentException(
                    "Invalid passport number. Use 6 to 9 letters or digits.");
        }

        if (isIdDuplicate(passport)) {
            throw new IllegalArgumentException(
                    "A guest with this passport is already registered.");
        }

        K2703411_Guest guest = new K2703411_InternationalGuest(
                generateGuestId(), passport, fullName, contact, email);

        guests.put(guest.getGuestId(), guest);

        return guest;
    }

    //return the guest object marching to searched guestID
    public K2703411_Guest findById(String guestId) {
        if (guestId == null || guestId.trim().isEmpty()) {
            return null;
        }
        return guests.get(guestId.trim());
    }

    //return an arraylist for searched name
    public List<K2703411_Guest> searchGuestsByName(String name) {
        List<K2703411_Guest> result = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return result;
        }
        String text = name.trim().toLowerCase();
        for (K2703411_Guest guest : guests.values()) {
            if (guest.getFullName().toLowerCase().contains(text)) {
                result.add(guest);
            }
        }
        return result;
    }

    //return all guests
    public List<K2703411_Guest> getAllGuests() {
        return new ArrayList<>(guests.values());
    }

    //validate the guest details
    private void validateCommonDetails(String fullName, String contact, String email) {
        if (!K2703411_InputValidator.isValidName(fullName)) {
            throw new IllegalArgumentException(
                    "Invalid name.");
        }
        if (!K2703411_InputValidator.isValidContactNumber(contact)) {
            throw new IllegalArgumentException(
                    "Invalid contact number.");
        }
        if (!K2703411_InputValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address.");
        }
    }

    //generate the next guest id
    private String generateGuestId() {
        guestCounter++;
        return String.format("G%03d", guestCounter);
    }
    
    // Verify if the guest identification number already exists
    private boolean isIdDuplicate(String idNumber) {
        String clean = idNumber.trim();

        for (K2703411_Guest guest : guests.values()) {
            if (guest.getIdentificationNumber().equalsIgnoreCase(clean)) {
                return true;
            }
        }
        return false;
    }
}



