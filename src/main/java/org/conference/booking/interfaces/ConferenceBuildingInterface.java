package org.conference.booking.interfaces;

public interface ConferenceBuildingInterface {
    void addBuilding(String buildingId);

    void addFloor(String buildingId, String floorId) throws Exception;

    void addRoom(String buildingId, String floorNumber, String roomNumber) throws Exception;
}
