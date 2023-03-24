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
public class Building {
    private String buildingId;
    private Map<String, Floor> buildingIdToFloorMap = new ConcurrentHashMap<>();

}
