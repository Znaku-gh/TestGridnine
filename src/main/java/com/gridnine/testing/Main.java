package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("All flights:");
        flights.forEach(System.out::println);

        // Фильтрация по вылету после текущего момента времени
        System.out.println("\nFlights without segments having arrival after now:");
        List<Flight> departureAfterNowFlights = new FlightFilter(flights)
                .addFilter(new DepartureAfterNowFilter())
                .filter();
        departureAfterNowFlights.forEach(System.out::println);

        // Фильтрация по сегментам с датой прилета позже даты вылета
        System.out.println("\nFlights without segments having arrival after departure:");
        List<Flight> validArrivalFlights = new FlightFilter(flights)
                .addFilter(new ArrivalAfterDepartureFilter())
                .filter();
        validArrivalFlights.forEach(System.out::println);

        // Фильтрация по общему времени на земле не более двух часов
        System.out.println("\nFlights with ground time less than or equal to two hours:");
        List<Flight> groundTimeValidFlights = new FlightFilter(flights)
                .addFilter(new GroundTimeUnderTwoHoursFilter())
                .filter();
        groundTimeValidFlights.forEach(System.out::println);

        // Комплексная фильтрация по всем критериям
        System.out.println("\nFlights filtered by all criteria:");
        List<Flight> complexFilteredFlights = new FlightFilter(flights)
                .addFilter(new DepartureAfterNowFilter())
                .addFilter(new ArrivalAfterDepartureFilter())
                .addFilter(new GroundTimeUnderTwoHoursFilter())
                .filter();
        complexFilteredFlights.forEach(System.out::println);
    }
}