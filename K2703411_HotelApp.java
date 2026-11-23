package com.mycompany.hotelapp;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class K2703411_HotelApp {
    
    private final K2703411_RoomManager roomManager = new K2703411_RoomManager();
    private final K2703411_GuestManager guestManager = new K2703411_GuestManager();
    private final K2703411_BookingManager bookingManager = new K2703411_BookingManager(roomManager, guestManager);
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        K2703411_HotelApp app = new K2703411_HotelApp();
        app.run();
    }
    
    // first menu user sees when start the application
    private void run(){
        boolean run = true;
        while(run){
            System.out.println("------------------------------------------------------");
            System.out.println("            SMARTSTAY HOTEL BOOKING SYSTEM            ");
            System.out.println("------------------------------------------------------");
            System.out.println("1. Administrator");
            System.out.println("2. Guest");
            System.out.println("0. Exit");
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice){
                case 1: showAdminMenu(); break;
                case 2: showGuestMenu(); break;
                case 0: run = false; break;
                default:System.out.println("Invalid choice. Please try again.");
            }
        }
        System.out.println("Goodbye!");
    }
    //menu users sees if select Administrator option
    private void showAdminMenu(){
        boolean back = false;
        while(!back){
            System.out.println("------------------------------------------------------");
            System.out.println("                  ADMINISTRATOR MENU                  ");
            System.out.println("------------------------------------------------------");
            System.out.println("1. Add a room");
            System.out.println("2. Update a room");
            System.out.println("3. Remove a room");
            System.out.println("4. Change room availability status");
            System.out.println("5. View all rooms");
            System.out.println("0. Back to main menu");
            int choice = getIntInput("Enter your choice: ");
            
            switch(choice){
                case 1:
                    adminAddRoom();
                    break;
                case 2:
                    adminUpdateRoom();
                    break;
                case 3:
                    adminRemoveRoom();
                    break;
                case 4:
                    adminChangeRoomStatus();
                    break;
                case 5:
                    adminViewRooms();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    //menu users sees if select Guest option 
    private void showGuestMenu(){
        boolean back = false;
        while (!back){
            System.out.println("------------------------------------------------------");
            System.out.println("                      GUEST MENU                      ");
            System.out.println("------------------------------------------------------");
            System.out.println("1. Register as a guest");
            System.out.println("2. Search available rooms");
            System.out.println("3. Make a reservation");
            System.out.println("4. Search reservations (guest name or booking ID)");
            System.out.println("5. View reservations by check-in date");
            System.out.println("6. Cancel a reservation");
            System.out.println("7. View invoice");
            System.out.println("0. Back to main menu");
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    guestRegister();
                    break;
                case 2:
                    guestSearchRooms();
                    break;
                case 3:
                    guestMakeBooking();
                    break;
                case 4:
                    guestSearchBookings();
                    break;
                case 5:
                    guestViewByCheckIn();
                    break;
                case 6:
                    guestCancelBooking();
                    break;
                case 7:
                    guestViewInvoice();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    //menu method to add rooms
    private void adminAddRoom(){
        System.out.println("--- Add a room ---");
        String roomId = getStringInput("Room ID: ");
        K2703411_RoomType type = readRoomType();
        K2703411_BedType bedType = readBedType();
        K2703411_Room room;
        switch (type) {
            case STANDARD:
                room = new K2703411_StandardRoom(roomId, bedType); break;
            case DELUXE:
                room = new K2703411_DeluxeRoom(roomId, bedType); break;
            case SUITE:
                room = new K2703411_SuiteRoom(roomId, bedType); break;
            case FAMILY_SUITE:
                room = new K2703411_FamilySuiteRoom(roomId, bedType); break;
            default:
                throw new IllegalArgumentException("Unknown room type.");
        }
        
        if(roomManager.addRoom(room)){
            System.out.println("Room added successfully.");
        }
        else {
                System.out.println("A room with ID " + room.getRoomId() + " already exists.");
        }  
    }
    
    //menu method to update rooms
    private void adminUpdateRoom(){
        System.out.println("-- Update a room ---");
        String roomId = getStringInput("Room ID: ");
        if(roomManager.findRoomById(roomId) == null){
            System.out.println("Room not found");
            return;
        }
        System.out.println("Enter the new details.");
        K2703411_RoomType type = readRoomType();
        K2703411_BedType bedType = readBedType();
        K2703411_Room update;
        switch (type) {
            case STANDARD:
                update = new K2703411_StandardRoom(roomId, bedType); break;
            case DELUXE:
                update = new K2703411_DeluxeRoom(roomId, bedType); break;
            case SUITE:
                update = new K2703411_SuiteRoom(roomId, bedType); break;
            case FAMILY_SUITE:
                update = new K2703411_FamilySuiteRoom(roomId, bedType); break;
            default:
                throw new IllegalArgumentException("Unknown room type.");
        }
        try{
            if(roomManager.updateRoom(update)){
                System.out.println("Room updated successfully.");
            }
            else{
                System.out.println("Room not found.");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println("Could not update room: " + e.getMessage());
        } 
    }
    
    //helper method to read the bed type
    private K2703411_BedType readBedType(){  
       System.out.println("Bed types: 1. Single  2. Double  3. Queen  4. King");  
       while (true){ 
           int choice = getIntInput("Choose bed type: "); 
           switch (choice) {
                case 1:
                    return K2703411_BedType.SINGLE;
                case 2:
                    return K2703411_BedType.DOUBLE;
                case 3:
                    return K2703411_BedType.QUEEN;
                case 4:
                    return K2703411_BedType.KING;
                default:
                    System.out.println("Invalid choice. Enter a number from 1 to 4.");
           }
       }      
    }
    
    //helper method to read the room type
    private K2703411_RoomType readRoomType() {
        System.out.println("Room types: 1. Standard  2. Deluxe  3. Suite  4. Family Suite");
        while (true) {
            int choice = getIntInput("Choose room type: ");
            switch (choice) {
                case 1:
                    return K2703411_RoomType.STANDARD;
                case 2:
                    return K2703411_RoomType.DELUXE;
                case 3:
                    return K2703411_RoomType.SUITE;
                case 4:
                    return K2703411_RoomType.FAMILY_SUITE;
                default:
                    System.out.println("Invalid choice. Enter a number from 1 to 4.");
            }
        }
    }
    
    //helper method to read the room status
    private K2703411_RoomStatus readRoomStatus() {
        System.out.println("Status: 1. Available  2. Reserved  3. Under maintenance");
        while (true) {
            int choice = getIntInput("Choose status (1-3): ");
            switch (choice) {
                case 1:
                    return K2703411_RoomStatus.AVAILABLE;
                case 2:
                    return K2703411_RoomStatus.RESERVED;
                case 3:
                    return K2703411_RoomStatus.UNDER_MAINTENANCE;
                default:
                    System.out.println("Invalid choice. Enter a number from 1 to 3.");
            }
        }
    }
     
    //takes string inputs safely
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    //takes integer inputs safely
    private int getIntInput(String prompt){
        while(true){
           System.out.print(prompt); 
           String input = scanner.nextLine().trim();
           
           try{
               return Integer.parseInt(input);
           }
           catch(NumberFormatException e){
               System.out.println("Invalid input. Please enter a valid number.");
           }
        }
    }
    
    //takes date inputs safely
    private LocalDate getDateInput(String prompt){
        while(true){
            System.out.print(prompt);
            String input = scanner.nextLine().trim(); 
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use the format yyyy-MM-dd, for example 2026-12-25.");
            }
        }
    }
    
    //menu method to remove a room using given room ID
    private void adminRemoveRoom() {
        System.out.println("\n--- Remove a room ---");
        String roomId = getStringInput("Room ID to remove: ");
        if (roomManager.removeRoom(roomId)) {
            System.out.println("Room removed successfully.");
        } else {
            System.out.println("Room not found.");
        }
    }
    
    //menu method to change the status of the room
    private void adminChangeRoomStatus(){
        System.out.println("--- Change room availability status ---");
        String roomId = getStringInput("Room ID to change status: ");
        if(roomManager.findRoomById(roomId) == null){
            System.out.println("Room not found");
            return;
        }
        K2703411_RoomStatus status = readRoomStatus();
        if(roomManager.updateRoomStatus(roomId, status)){
            System.out.println("Room status changed to " + status + ".");
        }
        else{
           System.out.println("Could not change the room status."); 
        }
        
    }
    
    //return all rooms in the system
    private void adminViewRooms(){
        System.out.println("--- All rooms ---");
        List<K2703411_Room> rooms = roomManager.getAllRooms();
        if(rooms.isEmpty()){
            System.out.println("no rooms found yet");
            return;
        }
        for(K2703411_Room room: rooms){
            System.out.println(room.getRoomDetails());
        }
    }
  
    //menu method to register a guest
    private void guestRegister(){
        System.out.println("--- Guest registration ---");  
        System.out.println("1. Local guest (NIC)");
        System.out.println("2. International guest (passport)");
        int type = getIntInput("Guest type: ");
        if(type != 1 && type != 2){
            System.out.println("Invalid guest type.");
            return;
        }
        
        String idNumber;
        if (type == 1){
            idNumber = getStringInput("NIC: ");
        }
        else{
            idNumber = getStringInput("Passport number: ");
        }
        String fullName = getStringInput("Full name: ");
        String contact = getStringInput("Contact number: ");
        String email = getStringInput("Email address: ");
        
        try{
            K2703411_Guest guest;
            if(type == 1){
               guest = guestManager.registerLocalGuest(idNumber, fullName, contact, email); 
            }
            else{
                guest = guestManager.registerInternationalGuest(idNumber, fullName, contact, email);
            }
            System.out.println("Registration successful!");
            System.out.println("Your Guest ID is: " + guest.getGuestId() + " (keep it for making reservations)");
        }
        catch(IllegalArgumentException e){
            System.out.println("Registration failed: " + e.getMessage());
        }
    }
    
    //menu method for guests to search for available rooms for the selected time period
    private void guestSearchRooms(){
        System.out.println("--- Search available rooms ---");
        K2703411_RoomType type = readRoomType();
        LocalDate checkIn = getDateInput("Check-in date (yyyy-MM-dd): ");
        LocalDate checkOut = getDateInput("Check-out date (yyyy-MM-dd): ");
        try{
            List<K2703411_Room> rooms = bookingManager.searchAvailableRooms(type, checkIn, checkOut);
            if(rooms.isEmpty()){    
                System.out.println("No " + type + " rooms are available for those dates.");
                return; 
            }
            for (K2703411_Room room : rooms) {
                System.out.println(room.getRoomDetails());
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Search failed: " + e.getMessage());
        }
    }
    
    //recieve the inputs for a booking and pass them to createBooking() in BookingManager
    private void guestMakeBooking(){
        System.out.println("--- Make a reservation ---");
        String guestId = getStringInput("Your Guest ID: ");
        String roomId = getStringInput("Room ID: ");
        int numberOfGuests = getIntInput("Number of guests: ");
        LocalDate checkIn = getDateInput("Check-in date (yyyy-MM-dd): ");
        LocalDate checkOut = getDateInput("Check-out date (yyyy-MM-dd): ");
        try{
            K2703411_Booking booking = bookingManager.createBooking(guestId, roomId, numberOfGuests, checkIn, checkOut);
            System.out.println("Reservation confirmed!");
            displayBooking(booking);
            System.out.println("Refundable booking advance: " + K2703411_Booking.ADVANCE_AMOUNT);
            
        }
        catch(IllegalArgumentException e){
            System.out.println("Reservation failed: " + e.getMessage());
        }
    }

    //menu method for guests to search for bookings using booking id
    private void guestSearchBookings(){
        System.out.println("--- Search reservations ---");
        System.out.println("1. By guest name");
        System.out.println("2. By booking ID");
        int choice = getIntInput("Search by: ");
        if(choice == 1){ 
            String name = getStringInput("Guest name: "); 
            List<K2703411_Booking> bookings = bookingManager.searchByGuestName(name);
            
            if(bookings.isEmpty()){
                System.out.println("No reservations found.");
            }
            else{
                for (K2703411_Booking booking : bookings) {
                System.out.println("--------------------------------");
                displayBooking(booking);
                }
            }
        }
        else if (choice == 2){
            String bookingId = getStringInput("Booking ID: ");
            K2703411_Booking booking = bookingManager.findBookingById(bookingId);
            
            if(booking == null){
                System.out.println("No reservation found with that booking ID.");
            }
            else{
                displayBooking(booking);
            }
        }
        else{
            System.out.println("Invalid choice.");
        }
    }
    
    //menu methos for guests to display the booking details
    private void displayBooking(K2703411_Booking booking){
        K2703411_Guest guest = booking.getGuest();
        K2703411_Room room = booking.getRoom();
        System.out.println("Booking ID : " + booking.getBookingId());
        System.out.println("Guest      : " + guest.getFullName() + " (" + guest.getGuestId() + ")");
        System.out.println("Room       : " + room.getRoomId() + " - " + room.getRoomType() + ", " + room.getBedType());
        System.out.println("Check-in   : " + booking.getCheckInDate());
        System.out.println("Check-out  : " + booking.getCheckOutDate());
        System.out.println("Nights     : " + booking.getNumberOfNights());
        System.out.println("Guests     : " + booking.getNumberOfGuests());
        System.out.println("Status     : " + booking.getStatus());
    }

    //meny method for guests to check for bookings using check-in date
    private void guestViewByCheckIn(){
        System.out.println("View reservations by check-in date ---");
        LocalDate date = getDateInput("Check-in date (yyyy-MM-dd): ");
        List<K2703411_Booking> bookings = bookingManager.viewByCheckInDate(date);
        
        if (bookings.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        for (K2703411_Booking booking : bookings){
            displayBooking(booking);
        }
    }
    
    //menu method for guests to cancel confirmed bookings.
    private void guestCancelBooking(){
        System.out.println("--- Cancel a reservation ---");
        String bookingId = getStringInput("Booking ID: ");
        try{
           if (bookingManager.cancelBooking(bookingId)) {
                System.out.println("Reservation cancelled. Your booking advance is refundable.");
            } else {
                System.out.println("No reservation found with that booking ID.");
            } 
        }
        catch(IllegalStateException e){
           System.out.println("Cannot cancel: " + e.getMessage()); 
        }
    }
    
    //menu method for guests to get the invioce for the booking. 
    private void guestViewInvoice(){
        System.out.println("--- View invoice ---"); 
        String bookingId = getStringInput("Booking ID: ");
        try {
            K2703411_Invoice invoice = bookingManager.generateInvoice(bookingId);
            invoice.printInvoice();
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot create invoice: " + e.getMessage());
        }
    }
}
