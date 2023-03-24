package org.conference.booking.Models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Booking {
    private final String bookingId = UUID.randomUUID().toString();
    private Slot slot;
    private User user;
    private String buildingId;
}
