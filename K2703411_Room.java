package com.mycompany.hotelapp;

public abstract class K2703411_Room {
    protected final String roomId;       
    protected final K2703411_RoomType roomType;
    protected K2703411_BedType bedType;
    protected K2703411_RoomStatus status;
    
    //constructor
    protected K2703411_Room(String roomId, K2703411_RoomType roomType, K2703411_BedType bedType) { 
        
        if (roomId == null || roomId.trim().isEmpty()) {
            throw new IllegalArgumentException("Room ID cannot be empty.");
        }
        if (roomType == null) {
            throw new IllegalArgumentException("Room type cannot be null.");
        }
        if (bedType == null) {
            throw new IllegalArgumentException("Bed type cannot be null.");
        }
        
        this.roomId = roomId.trim(); 
        this.roomType = roomType;
        this.bedType = bedType;
        this.status = K2703411_RoomStatus.AVAILABLE;
    }  
    
    //getters
    public String getRoomId() {       
        return roomId;
    }

    public K2703411_RoomType getRoomType() {
        return roomType;
    }

    public K2703411_BedType getBedType() {
        return bedType;
    }

    public K2703411_RoomStatus getStatus() {
        return status;
    }
    
    //setter to change room status
    public void setStatus(K2703411_RoomStatus status) {           
        if (status == null) {
            throw new IllegalArgumentException("Room status cannot be null.");
        }
        this.status = status;
    }
    
    //return a boolean to check if the room is available or not.
    public boolean isAvailable() {   
        return status == K2703411_RoomStatus.AVAILABLE;
    }
    
    //return details of a room object
    public String getRoomDetails() {  
        return String.format("Room ID: %s | Type: %s | Bed: %s | Daily Rate: LKR %,.2f | Status: %s",
                roomId, roomType, bedType, getDailyRate(), status);
    }
    
    //abstract methods
    public abstract double getDailyRate();         
    public abstract double getExtraGuestCharge();
    public abstract double getTaxRate();
    public abstract int getRoomCapacity();
    public abstract boolean isBreakfastIncluded();
}





