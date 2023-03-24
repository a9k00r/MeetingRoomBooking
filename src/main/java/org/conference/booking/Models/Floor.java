package org.conference.booking.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Floor {
    private String floorId;
    private Map<String, Room> floorIdToRoomMap = new ConcurrentHashMap<>();
}
