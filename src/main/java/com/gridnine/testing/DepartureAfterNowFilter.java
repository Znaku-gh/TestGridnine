package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.function.Predicate;

public class DepartureAfterNowFilter implements Predicate<Flight> {
    @Override
    public boolean test(Flight flight) {
        LocalDateTime now = LocalDateTime.now();
        return flight.getSegments().stream()
                .noneMatch(segment -> segment.getDepartureDate().isBefore(now));
    }
}
