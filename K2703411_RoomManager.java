package com.mycompany.hotelapp;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class K2703411_RoomManager {

    //LinkedHashMap to store room objects
    private Map<String, K2703411_Room> rooms = new LinkedHashMap<>();
    
    //add room object to the collection
    public boolean addRoom(K2703411_Room room) {
        if (room == null || rooms.containsKey(room.getRoomId())) {
            return false;
        }
        rooms.put(room.getRoomId(), room);
        return true;
    }

    //update existing room 
    public boolean updateRoom(K2703411_Room updatedRoom) {
        if (updatedRoom == null) {
            return false;
        }
        K2703411_Room existing = rooms.get(updatedRoom.getRoomId());
        if (existing == null) {
            return false;
        }
        updatedRoom.setStatus(existing.getStatus());
        rooms.put(updatedRoom.getRoomId(), updatedRoom);
        return true;
    }

    //remove a existing room
    public boolean removeRoom(String roomId) {
        if (roomId == null) {
            return false;
        }
        String key = roomId.trim();
        if (!rooms.containsKey(key)) {
            return false;
        }
        rooms.remove(key);
        return true;
    }

    //update the room status
    public boolean updateRoomStatus(String roomId, K2703411_RoomStatus status) {
        K2703411_Room room = findRoomById(roomId);
        if (room == null || status == null) {
            return false;
        }
        room.setStatus(status);
        return true;
    }

    //find a room using roomid
    public K2703411_Room findRoomById(String roomId) {
        if (roomId == null) {
            return null;
        }
        String key = roomId.trim();
        return rooms.get(key);
    }

    //this creates a new array list and pass all the room objects to it 
    public List<K2703411_Room> getAllRooms() {     
        return new ArrayList<>(rooms.values());
    }

    public List<K2703411_Room> getRoomsByType(K2703411_RoomType type) {
        List<K2703411_Room> relevant = new ArrayList<>();

        for (K2703411_Room room : rooms.values()) {
            if (room.getRoomType() == type) {
                relevant.add(room);
            }
        }
        return relevant;
    }
}
