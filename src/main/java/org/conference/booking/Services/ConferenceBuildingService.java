package org.conference.booking.Services;

import org.conference.booking.Models.Building;
import org.conference.booking.Models.Floor;
import org.conference.booking.Models.Room;
import org.conference.booking.Models.Slot;
import org.conference.booking.interfaces.ConferenceBuildingInterface;
import org.conference.booking.persistence.Persistence;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ConferenceBuildingService implements ConferenceBuildingInterface {


    @Override
    public void addBuilding(@NotBlank String buildingId) {
        if (!Persistence.buildingIdToBuildingMap.containsKey(buildingId)) {
            Building building = Building.builder().buildingId(buildingId).buildingIdToFloorMap(new HashMap<>()).build();
            Persistence.buildingIdToBuildingMap.put(buildingId, building);
            System.out.printf("Added building %s into the system.%n", buildingId);
            return;
        }

        System.out.println("Building is already added");
    }

    @Override
    public void addFloor(final @NotBlank String buildingId, final @NotBlank String floorId) throws Exception {
        if (!Persistence.buildingIdToBuildingMap.containsKey(buildingId)) {
            throw new Exception("Invalid buildingId");
        }

        Floor floor = Floor.builder().floorId(floorId).floorIdToRoomMap(new HashMap<>()).build();

        Persistence.buildingIdToBuildingMap.get(buildingId).getBuildingIdToFloorMap().put(buildingId, floor);

        System.out.printf("Added floor %s into the building %s.%n", floorId, buildingId);

    }

    @Override
    public void addRoom(final @NotBlank String buildingId, final @NotBlank String floorNumber, final @NotBlank String roomNumber) throws Exception {
        if (!Persistence.buildingIdToBuildingMap.containsKey(buildingId)) {
            throw new Exception("Invalid buildingId");
        }

        Room room = Room.builder().roomNumber(roomNumber).slot(new Slot()).build();

        Persistence.buildingIdToBuildingMap.get(buildingId)
                .getBuildingIdToFloorMap()
                .get(buildingId)
                .getFloorIdToRoomMap()
                .put(floorNumber, room);

        System.out.printf("Added room number %s into the building number %s with floor number %s%n", roomNumber, buildingId, floorNumber);
    }
}
