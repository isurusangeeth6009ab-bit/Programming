package com.mycompany.hotelapp;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class K2703411_CancellationTest {

    public static void main(String[] args) {

        // Create a guest
        K2703411_Guest guest = new K2703411_LocalGuest(
                "G001",
                "200012345678",
                "Amal Perera",
                "0771234567",
                "amal@example.com"
        );

        // Create a room
        K2703411_Room room = new K2703411_DeluxeRoom(
                "R101",
                K2703411_BedType.QUEEN
        );

        // Create a booking
        K2703411_Booking booking = new K2703411_Booking(
                "B001",
                guest,
                room,
                4,
                LocalDate.of(2026, 12, 20),
                LocalDate.of(2026, 12, 25)
        );

        // Attempt cancellation within 24 hours of check-in
        LocalDateTime testTime = LocalDateTime.of(2026, 12, 19, 14, 1);

        boolean actual = booking.canModifyOrCancel(testTime);

        System.out.println("TC019 - Cancellation Cutoff Validation");
        System.out.println("Check-in: 2026-12-20 14:00");
        System.out.println("Cancellation request: " + testTime);
        System.out.println("Expected result: false");
        System.out.println("Actual result: " + actual);

        if (!actual) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }
    }
}

