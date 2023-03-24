package org.conference.booking.Services;

import org.conference.booking.Models.Booking;
import org.conference.booking.Models.Building;
import org.conference.booking.Models.Slot;
import org.conference.booking.Models.User;
import org.conference.booking.interfaces.BookingInterface;
import org.conference.booking.interfaces.SearchInterface;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Service;
import org.conference.booking.persistence.Persistence;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService implements BookingInterface {
    SearchInterface searchInterface = new SearchService();

    @Override
    public void book(final @NotNull User userId, final @NotBlank String buildingId, final @NotBlank String floorId,
                     final @NotBlank String roomId, @NotBlank Slot slot) throws Exception {
        validate(buildingId, floorId, roomId, slot);

        boolean isAvailable = searchInterface.searchAvailableRoom(buildingId, floorId, slot);
        if (!isAvailable) {
            throw new Exception("slot already booked");
        }

        Slot bookedSlot = Slot.builder().booked(true).start(slot.getStart()).end(slot.getEnd()).build();

        Booking newBooking = Booking.builder().user(userId).buildingId(buildingId).slot(bookedSlot).build();
        Persistence.userIdToBookingMap.put(userId.getUserId(), newBooking);
        Persistence.buildingIdToBuildingMap.get(buildingId).getBuildingIdToFloorMap().get(buildingId)
                .getFloorIdToRoomMap().get(floorId).setSlot(slot);

        System.out.printf("successfully booked the slot with bookingId %s", newBooking.getBookingId());

    }

    private void validate(String buildingId, String floorId, String roomId, Slot slot) throws Exception {
        if (!Persistence.buildingIdToBuildingMap.containsKey(buildingId)) {
            throw new Exception("Invalid buildingId");
        }

        if (slot.getStart() < 1 || slot.getEnd() > 24) {
            throw new Exception("invalid slot");
        }
    }

    @Override
    public void cancel(final @NotNull User user, final @NotBlank String buildingId, final @NotBlank String floorId, final @NotBlank String roomId, final @NotNull Slot slot) throws Exception {
        validate(buildingId, floorId, roomId, slot);
        if (!Persistence.userIdToBookingMap.containsKey(user.getUserId())) {
            throw new Exception("Invalid userId");
        }
        Persistence.userIdToBookingMap.remove(user.getUserId());
        Building building = Persistence.buildingIdToBuildingMap.get(buildingId);
        building.getBuildingIdToFloorMap().get(buildingId).getFloorIdToRoomMap().get(floorId).getSlot().setBooked(false);

        Persistence.buildingIdToBuildingMap.put(buildingId, building);
        System.out.println("successfully canceled the booking");
    }

    @Override
    public List<Booking> getBookingList(final @NotBlank String buildingId, final @NotBlank String floorId) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : Persistence.userIdToBookingMap.values()) {
            if (buildingId.equalsIgnoreCase(booking.getBuildingId())) {
                result.add(booking);
            }
        }

        System.out.println(result);
        return result;
    }

}
