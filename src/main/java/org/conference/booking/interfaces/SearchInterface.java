package org.conference.booking.interfaces;

import org.conference.booking.Models.Slot;

public interface SearchInterface {
    boolean searchAvailableRoom(String buildingId, String floorId, Slot slot) throws Exception;

}
