package com.mycompany.hotelapp;
import java.time.LocalDate;

public class K2703411_Invoice {
    private static final double DISCOUNT_RATE = 0.12; //discount rate
    private static final int DISCOUNT_MIN_NIGHTS = 5; //minimum days needed to recieve the discount
    
    private final String invoiceId;
    private final K2703411_Booking booking;
    private final LocalDate issueDate;
    private final double baseRoomCharge;
    private final double extraGuestCharge;
    private final double discountAmount;
    private final double taxAmount;
    private final double advanceDeducted;
    private final double finalAmount;
    
    //constructor 
    public K2703411_Invoice(String invoiceId, K2703411_Booking booking){ 
        if(invoiceId == null || invoiceId.trim().isEmpty()){
            throw new IllegalArgumentException("Invoice ID cannot be empty.");
        }
        if(booking == null){
            throw new IllegalArgumentException("Booking cannot be empty.");
        }
        this.invoiceId = invoiceId.trim();
        this.booking = booking;
        this.issueDate = LocalDate.now();
        
        this.baseRoomCharge = calBaseRoomCharge();
        this.extraGuestCharge = calculateExtraGuestCharge();
        this.discountAmount = calculateDiscount(baseRoomCharge);
        double taxableAmount = baseRoomCharge + extraGuestCharge - discountAmount;
        this.taxAmount = calculateTax(taxableAmount);
        this.advanceDeducted = K2703411_Booking.ADVANCE_AMOUNT;
        this.finalAmount = taxableAmount + taxAmount - advanceDeducted;
    }
    
    //calculate the base room charge
    private double calBaseRoomCharge(){
        double baseCharge = booking.getRoom().getDailyRate() * booking.getNumberOfNights();
        return baseCharge;   
    }
    
    //calculate the extra guest charge. return zero if none. 
    private double calculateExtraGuestCharge(){
        K2703411_Room room = booking.getRoom();
        int extraGuests = booking.getNumberOfGuests() - room.getRoomCapacity();
        if(extraGuests <= 0){
            return 0.0;
        }
        return room.getExtraGuestCharge() * booking.getNumberOfNights() * extraGuests;
    }
    
    //calculate the discount. retuen 0 if none. 
    private double calculateDiscount(double baseCharge){
        if(booking.getNumberOfNights() >= DISCOUNT_MIN_NIGHTS){
            return baseCharge * DISCOUNT_RATE;
        }
        return 0.0;
    }
    
    //calculate the tax usng to tax rate of a specific room type.
    private double calculateTax(double amount){
        return amount * booking.getRoom().getTaxRate();
    }
    
    //calculate the final amount
    public double calculateFinalAmount() {
        return baseRoomCharge + extraGuestCharge - discountAmount + taxAmount - advanceDeducted;
    }
    
    //getter to return the final amount calculated in the calculateFinalAmount() 
    public double getFinalAmount() {
        return finalAmount;
    } 
    
    //print the invoice of the booking
    public void printInvoice(){
        K2703411_Room room = booking.getRoom();
        K2703411_Guest guest = booking.getGuest();
        
        System.out.println("------------------------------------------------------------");
        System.out.println("                SMARTSTAY HOTEL INVOICE");
        System.out.println("------------------------------------------------------------");
        System.out.printf("Invoice ID    : %s%n", invoiceId);
        System.out.printf("Issue date    : %s%n", issueDate);
        System.out.printf("Booking ID    : %s%n", booking.getBookingId());
        System.out.println("------------------------------------------------------------");
        System.out.println("GUEST DETAILS");
        System.out.printf("Guest ID      : %s%n", guest.getGuestId());
        System.out.printf("Name          : %s%n", guest.getFullName());
        System.out.printf("Guest type    : %s%n", guest.getGuestType());
        System.out.printf("NIC/Passport  : %s%n", guest.getIdentificationNumber());
        System.out.printf("Contact       : %s%n", guest.getContactNumber());
        System.out.printf("Email         : %s%n", guest.getEmail());
        System.out.println("------------------------------------------------------------");
        System.out.println("ROOM DETAILS");
        System.out.printf("Room ID       : %s%n", room.getRoomId());
        System.out.printf("Room type     : %s%n", room.getRoomType());
        System.out.printf("Bed type      : %s%n", room.getBedType());
        System.out.printf("Daily rate    : LKR %,.2f%n", room.getDailyRate());
        System.out.printf("Breakfast     : %s%n", breakfastString(room));
        System.out.println("------------------------------------------------------------");
         System.out.println("STAY DETAILS");
        System.out.printf("Check-in      : %s%n", booking.getCheckInDate());
        System.out.printf("Check-out     : %s%n", booking.getCheckOutDate());
        System.out.printf("Nights        : %d%n", booking.getNumberOfNights());
        System.out.printf("Guests        : %d%n", booking.getNumberOfGuests());
        double shownDiscount = 0.0;
        if (discountAmount > 0) {
            shownDiscount = -discountAmount;
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("CHARGES");
        System.out.printf("Base room charge        : LKR %,12.2f%n", baseRoomCharge);
        System.out.printf("Extra guest charges     : LKR %,12.2f%n", extraGuestCharge);
        System.out.printf("Discount (%.0f%%)          : LKR %,12.2f%n", DISCOUNT_RATE * 100, shownDiscount);
        System.out.printf("Tax (%.0f%%)               : LKR %,12.2f%n", room.getTaxRate() * 100, taxAmount);
        System.out.printf("Booking advance deducted: LKR %,12.2f%n", -advanceDeducted);
        System.out.println("------------------------------------------------------------");
        if (finalAmount >= 0) {
            System.out.printf("FINAL PAYABLE AMOUNT    : LKR %,12.2f%n", finalAmount);
        } else {
            System.out.printf("REFUND DUE TO GUEST     : LKR %,12.2f%n", -finalAmount);
        }
        System.out.println("------------------------------------------------------------");   
    }
    
    //return "Complimentary" if the room has breakfast, otherwise return "Not included".
    private String breakfastString(K2703411_Room room){
        if(room.isBreakfastIncluded()){
            return "Complimentary";
        }
        else{
            return "Not included";
        }
    }  
}

