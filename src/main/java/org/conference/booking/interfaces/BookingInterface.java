package org.conference.booking.interfaces;

import org.conference.booking.Models.Booking;
import org.conference.booking.Models.Slot;
import org.conference.booking.Models.User;

import java.util.List;

public interface BookingInterface {
    void book(User userId, String buildingId, String floorId, String roomId, Slot slot) throws Exception;
    void cancel(User userId, String buildingId, String floorId, String roomId, Slot slot) throws Exception;
    List<Booking> getBookingList(String buildingId, String floorId);
}
