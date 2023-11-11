package com.dydek.flightsearch.controller;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.*;
import com.dydek.flightsearch.config.AmadeusConnection;
import org.springframework.web.bind.annotation.*;


@RestController
public class ApiController {
    @GetMapping("/")
    public String landingPage() {
        return "";
    }

    @GetMapping("/locations")
    public String airportLocations(@RequestParam() String keyword) {
        try {
            Location[] results = AmadeusConnection.INSTANCE.airportLocation(keyword);
            return AmadeusConnection.INSTANCE.toJson(results[0]);
        } catch (ResponseException e) {
            return e.getDescription();
        }
    }

    @GetMapping("/offers")
    public String flightOffers(@RequestParam() String originCode,
                               @RequestParam() String destinationCode,
                               @RequestParam() String departDate,
                               @RequestParam() String adultsNumber,
                               @RequestParam(defaultValue = "") String returnDate) {
        try {
            FlightOfferSearch[] flightOfferSearches = AmadeusConnection.INSTANCE.flightOfferSearches(originCode, destinationCode, departDate, returnDate, adultsNumber);
            return AmadeusConnection.INSTANCE.toJson(flightOfferSearches[0]);
        } catch (ResponseException e) {
            return e.getDescription();
        }
    }

    @PostMapping("/confirm")
    public String confirmFlightPrice(@RequestBody() FlightOfferSearch flightOfferSearch) {
        try {
            FlightPrice flightPrice = AmadeusConnection.INSTANCE.confirmFlightPrice(flightOfferSearch);
            return AmadeusConnection.INSTANCE.toJson(flightPrice);
        } catch (ResponseException e) {
            return e.getDescription();
        }
    }

    @GetMapping("/order")
    public String flightOrder(@RequestBody() FlightPrice flightPrice, Traveler[] travelers) {
        try {
            FlightOrder results = AmadeusConnection.INSTANCE.flightOrder(flightPrice, travelers);
            return AmadeusConnection.INSTANCE.toJson(results);
        } catch (ResponseException e) {
            return e.getDescription();
        }
    }
}

