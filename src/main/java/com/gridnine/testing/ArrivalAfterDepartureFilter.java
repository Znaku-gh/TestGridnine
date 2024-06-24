package com.gridnine.testing;

import java.util.function.Predicate;

public class ArrivalAfterDepartureFilter implements Predicate<Flight> {
    @Override
    public boolean test(Flight flight) {
        return flight.getSegments().stream()
                .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }
}
