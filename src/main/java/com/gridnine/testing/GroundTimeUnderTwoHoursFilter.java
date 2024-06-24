package com.gridnine.testing;

import java.time.Duration;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class GroundTimeUnderTwoHoursFilter implements Predicate<Flight> {
    @Override
    public boolean test(Flight flight) {
        return  IntStream.range(0, flight.getSegments().size() - 1)
                .mapToLong(i -> {
                    Segment currentSegment = flight.getSegments().get(i);
                    Segment nextSegment = flight.getSegments().get(i + 1);
                    return Duration.between(currentSegment.getArrivalDate(), nextSegment.getDepartureDate()).toMinutes();
                })
                .sum() <= 120;
    }
}
