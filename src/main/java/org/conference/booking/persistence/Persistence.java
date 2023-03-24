package org.conference.booking.persistence;

import org.conference.booking.Models.Booking;
import org.conference.booking.Models.Building;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class Persistence {
    private Persistence() {
    }

    public static Map<String, Building> buildingIdToBuildingMap = new ConcurrentHashMap<>();
    public static Map<String, Booking> userIdToBookingMap = new ConcurrentHashMap<>();


}
