package org.conference.booking.Services;

import org.conference.booking.Models.Booking;
import org.conference.booking.Models.Slot;
import org.conference.booking.interfaces.SearchInterface;
import org.conference.booking.persistence.Persistence;
import org.springframework.stereotype.Service;

@Service
public class SearchService implements SearchInterface {
    @Override
    public boolean searchAvailableRoom(String buildingId, String floorId, Slot slot) throws Exception {
        if (!validate(buildingId, floorId, slot)) {
            throw new Exception("Invalid input");
        }

        boolean isOverLapped = Persistence.userIdToBookingMap.values().stream()
                .filter(booking -> buildingId.equals(booking.getBuildingId()))
                .map(Booking::getSlot)
                .anyMatch(s -> !s.isNotOverLapped(slot));

        if (!isOverLapped) {
            System.out.println("slot is available");
            return true;
        } else {
            System.out.println("slot booked already");
            return false;
        }
    }

    private boolean validate(String buildingId, String floorId, Slot slot) {
        return (slot.getStart() > 0 && slot.getEnd() < 25) && Persistence.buildingIdToBuildingMap.containsKey(buildingId)
                && Persistence.buildingIdToBuildingMap.get(buildingId).getBuildingIdToFloorMap().get(buildingId).getFloorIdToRoomMap().containsKey(floorId);
    }

}
