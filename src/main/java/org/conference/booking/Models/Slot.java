package org.conference.booking.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Slot {
    private int start;
    private int end;

    private boolean booked = false;
    public boolean isNotOverLapped(Slot slot) {
        return slot.start >= end || slot.end <= start;
    }

}
