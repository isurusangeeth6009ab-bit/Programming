package com.mycompany.hotelapp;


public class K2703411_FamilySuiteRoom extends K2703411_Room {
    private static final double DAILY_RATE = 18000.00;
    private static final double EXTRA_GUEST_CHARGE = 2500.00;
    private static final double TAX_RATE = 0.15;       // 15%
    private static final int ROOM_CAPACITY = 5;        // assumption: case study gives no capacity
    private static final boolean BREAKFAST_INCLUDED = true;
    
    //constructor
    public K2703411_FamilySuiteRoom(String roomId, K2703411_BedType bedType) {
        super(roomId, K2703411_RoomType.FAMILY_SUITE, bedType);
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

