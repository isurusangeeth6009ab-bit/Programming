package com.mycompany.hotelapp;

public class K2703411_StandardRoom extends K2703411_Room {
    private static final double DAILY_RATE = 6000.00;
    private static final double EXTRA_GUEST_CHARGE = 1000.00;
    private static final double TAX_RATE = 0.10;       // 10%
    private static final int ROOM_CAPACITY = 2;        // assumption because case study gives no capacity
    private static final boolean BREAKFAST_INCLUDED = false;
    
    //constructor
    public K2703411_StandardRoom(String roomId, K2703411_BedType bedType) { 
        super(roomId, K2703411_RoomType.STANDARD, bedType);
    }
    
    @Override
    public double getDailyRate() {
        return DAILY_RATE;
    }
 
    @Override
    public double getExtraGuestCharge() {
        return EXTRA_GUEST_CHARGE;
    }
 
    @Override
    public double getTaxRate() {
        return TAX_RATE;
    }
 
    @Override
    public int getRoomCapacity() {
        return ROOM_CAPACITY;
    }
 
    @Override
    public boolean isBreakfastIncluded() {
        return BREAKFAST_INCLUDED;
    }
}

