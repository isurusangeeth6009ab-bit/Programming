package com.mycompany.hotelapp;
import java.util.regex.Pattern;

public class K2703411_InputValidator {
    private static final Pattern NIC_PATTERN = Pattern.compile("^(\\d{9}[VvXx]|\\d{12})$");
    private static final Pattern PASSPORT_PATTERN = Pattern.compile("^[A-Za-z0-9]{6,9}$");
    private static final Pattern CONTACT_PATTERN = Pattern.compile("^\\+?\\d{7,15}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z .'-]{1,59}$");

    public K2703411_InputValidator() {
    }
    
    public static boolean isValidName(String name) {
        return matches(NAME_PATTERN, name);
    }
    
    public static boolean isValidNIC(String nic) {
        return matches(NIC_PATTERN, nic);
    }
    
    public static boolean isValidPassport(String passport) {
        return matches(PASSPORT_PATTERN, passport);
    }
    
    public static boolean isValidContactNumber(String contact) {
        return matches(CONTACT_PATTERN, contact);
    }
    
     public static boolean isValidEmail(String email) {
        return matches(EMAIL_PATTERN, email);
    }
     
    private static boolean matches(Pattern pattern, String value) {
        if (value == null) {
            return false;
        }
        String trimmedValue = value.trim();
        return pattern.matcher(trimmedValue).matches();
    } 
}
