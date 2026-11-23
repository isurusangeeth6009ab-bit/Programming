package com.mycompany.hotelapp;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class K2703411_Booking {

    public static final double ADVANCE_AMOUNT = 10000.00;  //advance paid when booking a room
    public static final int CHANGE_CUTOFF_HOURS = 24; // Minimum hours required before check-in to cancel a booking
    private static final LocalTime CHECK_IN_TIME = LocalTime.of(14, 0); //check-in time (2:00 PM)

    private final String bookingId;
    private final K2703411_Guest guest;
    private final K2703411_Room room;
    private final int numberOfGuests;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private K2703411_BookingStatus status;
    
    //constructor
    public K2703411_Booking(String bookingId, K2703411_Guest guest, K2703411_Room room, int numberOfGuests, LocalDate checkInDate, LocalDate checkOutDate) {

        if (bookingId == null || bookingId.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking ID cannot be empty.");
        }
        if (guest == null) {
            throw new IllegalArgumentException("Guest cannot be null.");
        }
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null.");
        }
        if (numberOfGuests < 1) {
            throw new IllegalArgumentException("Number of guests cannot be 0.");
        }
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Check-in and check-out dates are required.");
        }
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new IllegalArgumentException("Check-out date must be after the check-in date.");
        }
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.numberOfGuests = numberOfGuests;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = K2703411_BookingStatus.CONFIRMED;
    }

    public String getBookingId() {
        return bookingId;
    }

    public K2703411_Guest getGuest() {
        return guest;
    }

    public K2703411_Room getRoom() {
        return room;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public K2703411_BookingStatus getStatus() {
        return status;
    }

    //calculate the number of nights using check-in and check-out
    public int getNumberOfNights() {
        int numberOfDays = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return numberOfDays;
    }
    
    //to test the cancellation using a given date and time
    public boolean canModifyOrCancel(LocalDateTime now) {  

        if (now == null || status != K2703411_BookingStatus.CONFIRMED) {
            return false;
        }

        LocalDateTime checkInDateTime
                = LocalDateTime.of(checkInDate, CHECK_IN_TIME);

        LocalDateTime cutoff
                = checkInDateTime.minusHours(CHANGE_CUTOFF_HOURS);

        return !now.isAfter(cutoff);
    }
    
    //canModifyOrCancel for the normal use of the system
    public boolean canModifyOrCancel() {
        return canModifyOrCancel(LocalDateTime.now());
    }

    //cancel the booking and change the status to CANCELLED
    public void cancel() {
        if (status != K2703411_BookingStatus.CONFIRMED) {
            throw new IllegalStateException("Only a confirmed booking can be cancelled.");
        }
        if (!canModifyOrCancel()) {
            throw new IllegalStateException(
                    "Bookings cannot be cancelled within " + CHANGE_CUTOFF_HOURS + " hours of check-in.");
        }
        status = K2703411_BookingStatus.CANCELLED;
                                           /*setStatus is done here after validations 
                                           without giving direct access to setStatus()*/
    }
}


