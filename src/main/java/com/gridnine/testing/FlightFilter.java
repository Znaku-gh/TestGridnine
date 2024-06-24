package com.gridnine.testing;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FlightFilter {
    private final List<Flight> flights;
    private final List<Predicate<Flight>> filters = new ArrayList<>();

    public FlightFilter(List<Flight> flights) {
        this.flights = flights;
    }

    public FlightFilter addFilter(Predicate<Flight> filter) {
        filters.add(filter);
        return this;
    }

    public List<Flight> filter() {
        return flights.stream().filter(
                filters.stream()
                .reduce(Predicate::and)
                .orElse(f -> true)
        ).toList();
    }
}
