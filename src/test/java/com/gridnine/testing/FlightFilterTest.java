package com.gridnine.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class FlightFilterTest {

    // Junit test for DepartureAfterNowFilter
    @Test
    @DisplayName("Test for DepartureAfterNowFilter")
    void givenListOfFlights_whenApplyDepartureAfterNowFilter_thenReturnCorrectFlight() {
        // given - precondition or setup
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        List<Flight> flights = Arrays.asList(
                //A flight departing in the past
                FlightBuilder.createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A normal multi segment flight
                FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5))
        );
        List<Flight> expextedFlights = flights.subList(1, 2);

        // when - action or the behaviour that we are going to test
        List<Flight> filteredFlights = new FlightFilter(flights)
                .addFilter(new DepartureAfterNowFilter())
                .filter();

        // then - verify the output
        Assertions.assertEquals(expextedFlights, filteredFlights);
    }

    // Junit test for ArrivalAfterDepartureFilter
    @Test
    @DisplayName("Test for ArrivalAfterDepartureFilter")
    void givenListOfFlights_whenApplyArrivalAfterDepartureFilter_thenReturnCorrectFlight() {
        // given - precondition or setup
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        List<Flight> flights = Arrays.asList(
                //A flight that departs before it arrives
                FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //A normal multi segment flight
                FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5))
        );
        List<Flight> expextedFlights = flights.subList(1, 2);

        // when - action or the behaviour that we are going to test
        List<Flight> filteredFlights = new FlightFilter(flights)
                .addFilter(new ArrivalAfterDepartureFilter())
                .filter();

        // then - verify the output
        Assertions.assertEquals(expextedFlights, filteredFlights);
    }

    // Junit test for GroundTimeUnderTwoHoursFilter
    @Test
    @DisplayName("Test for GroundTimeUnderTwoHoursFilter")
    void givenListOfFlights_whenApplyGroundTimeUnderTwoHoursFilter_thenReturnCorrectFlight() {
        // given - precondition or setup
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        List<Flight> flights = Arrays.asList(
                //A flight with more than two hours ground time
                FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //A normal multi segment flight
                FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5))
        );
        List<Flight> expextedFlights = flights.subList(1, 2);

        // when - action or the behaviour that we are going to test
        List<Flight> filteredFlights = new FlightFilter(flights)
                .addFilter(new GroundTimeUnderTwoHoursFilter())
                .filter();

        // then - verify the output
        Assertions.assertEquals(expextedFlights, filteredFlights);
    }

    @Test
    @DisplayName("Test for multiple filters")
    void givenListOfFlights_whenApplyMultipleFilters_thenReturnCorrectFlight() {
        // given - precondition or setup
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        List<Flight> flights = Arrays.asList(
                //A normal multi segment flight
                FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                FlightBuilder.createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A flight that departs before it arrives
                FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //A flight with more than two hours ground time
                FlightBuilder.createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6))

        );
        List<Flight> expextedFlights = flights.subList(0, 1);

        // when - action or the behaviour that we are going to test
        List<Flight> filteredFlights = new FlightFilter(flights)
                .addFilter(new ArrivalAfterDepartureFilter())
                .addFilter(new DepartureAfterNowFilter())
                .addFilter(new GroundTimeUnderTwoHoursFilter())
                .filter();

        // then - verify the output
        Assertions.assertEquals(expextedFlights, filteredFlights);
    }
}
