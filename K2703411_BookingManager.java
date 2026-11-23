package com.mycompany.hotelapp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class K2703411_BookingManager {
    private static final int MIN_ADVANCE_DAYS = 2; 
    private Map<String, K2703411_Booking> bookings = new LinkedHashMap<>(); //LinkedHashMap to store booking records 
    private int bookingCounter = 0; //to generate the next booking id
    private final K2703411_RoomManager roomManager;
    private final K2703411_GuestManager guestManager;
    
    public K2703411_BookingManager(K2703411_RoomManager roomManager, K2703411_GuestManager guestManager){
        if (roomManager == null || guestManager == null) {
            throw new IllegalArgumentException("RoomManager and GuestManager are required.");
        }
        this.roomManager = roomManager;
        this.guestManager = guestManager;
    }
    
    //create the booking and store in the LinkedHashMap
    public K2703411_Booking createBooking(String guestId, String roomId, int numberOfGuests,LocalDate checkIn, LocalDate checkOut){
        K2703411_Guest guest = guestManager.findById(guestId);
        if (guest == null) {
            throw new IllegalArgumentException("Guest not found. Please register first.");
        }
        K2703411_Room room = roomManager.findRoomById(roomId);
        if (room == null) {
            throw new IllegalArgumentException("Room not found.");
        }
        if (numberOfGuests < 1) {
            throw new IllegalArgumentException("Number of guests must be at least 1.");
        }
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out dates are required.");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out date must be after the check-in date.");
        }
        if (checkIn.isBefore(LocalDate.now().plusDays(MIN_ADVANCE_DAYS))) {
            throw new IllegalArgumentException("Bookings must be made at least " + MIN_ADVANCE_DAYS + " days in advance.");
        }
        if (!room.isAvailable()) {
            throw new IllegalArgumentException("This room cannot be booked (status: " + room.getStatus() + ").");
        }
        if (hasDateConflict(room.getRoomId(), checkIn, checkOut)) {
            throw new IllegalArgumentException("This room is already booked for the selected dates.");
        }
        K2703411_Booking booking = new K2703411_Booking(generateBookingId(), guest, room, numberOfGuests, checkIn, checkOut);
        bookings.put(booking.getBookingId(), booking);
        return booking;   
    }
    
    //check for date conflicts using the existing bookings 
    private boolean hasDateConflict(String roomId, LocalDate checkIn, LocalDate checkOut){
        for (K2703411_Booking exist : bookings.values()){
            if (exist.getStatus() != K2703411_BookingStatus.CONFIRMED) {
                continue;
            }
            if (!exist.getRoom().getRoomId().equals(roomId)) {
                continue;
            }
            boolean overlaps = exist.getCheckInDate().isBefore(checkOut) && checkIn.isBefore(exist.getCheckOutDate());
            if (overlaps) {
                return true;
            }
        }
        return false;
    }
    
    //generate the next booking id
    private String generateBookingId() {
        bookingCounter++;
        return String.format("B%03d", bookingCounter);
    }
     //cancel booking
    public boolean cancelBooking(String bookingId){
        K2703411_Booking booking = findBookingById(bookingId);
        if(booking == null){
            return false;
        }
        booking.cancel();
        return true;
    }
    
    //find the booking using id
    public K2703411_Booking findBookingById(String bookingId){ 
        if (bookingId == null || bookingId.trim().isEmpty()) {
            return null;
        } 
        return bookings.get(bookingId.trim());
    }
    
    //find the booking using given name
    public List<K2703411_Booking> searchByGuestName(String name){
        List<K2703411_Booking> result = new ArrayList<>();
        if(name == null || name.trim().isEmpty()){
            return result;
        }
        String searchedName = name.trim().toLowerCase();
        for (K2703411_Booking booking : bookings.values()){
            if(booking.getGuest().getFullName().toLowerCase().contains(searchedName)){
                result.add(booking);
            }
        }
        return result;   
    }
    
    //find the booking using given check-in date
    public List<K2703411_Booking> viewByCheckInDate(LocalDate date) {
        List<K2703411_Booking> result = new ArrayList<>();
        if (date == null) {
            return result;
        }
        for (K2703411_Booking booking : bookings.values()) {
            if (booking.getCheckInDate().equals(date)) {
                result.add(booking);
            }
        }
        return result;
    }
    
    //search for available rooms for the given check-in and check-out
    public List<K2703411_Room> searchAvailableRooms(K2703411_RoomType type, LocalDate checkIn, LocalDate checkOut){
        if(type == null){ 
            throw new IllegalArgumentException("Room type is required."); 
        }
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out dates are required.");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("Check-out date must be after the check-in date.");
        }
        
        if (checkIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past.");
        }
        
        List<K2703411_Room> result = new ArrayList<>();
        for (K2703411_Room room : roomManager.getRoomsByType(type)) {
            if (room.isAvailable() && !hasDateConflict(room.getRoomId(), checkIn, checkOut)) {
                result.add(room);
            }
        }
        return result;
    }
    
    //generate invoice using invoice class
    public K2703411_Invoice generateInvoice(String bookingId){
        K2703411_Booking booking = findBookingById(bookingId);
        if (booking == null){
            throw new IllegalArgumentException("Booking not found.");
        }
        return new K2703411_Invoice("INV-" + booking.getBookingId(), booking);
    }        
}
