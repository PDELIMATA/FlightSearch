package com.dydek.flightsearch.controller;

import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.*;
import com.dydek.flightsearch.DatabaseConnect;
import com.dydek.flightsearch.config.AmadeusConnection;
import com.google.gson.JsonObject;
import org.springframework.web.bind.annotation.*;


@RestController
public class ApiController {

    @GetMapping("/locations")
    public Location[] airportLocations(@RequestParam() String keyword) throws ResponseException {
        return AmadeusConnection.INSTANCE.airportLocation(keyword);
    }

    @GetMapping("/offers")
    public FlightOfferSearch[] flightOffers(@RequestParam() String originCode,
                                            @RequestParam() String destinationCode,
                                            @RequestParam() String departDate,
                                            @RequestParam() String adultsNumber,
                                            @RequestParam(defaultValue = "") String returnDate) throws ResponseException {

        return AmadeusConnection.INSTANCE.flightOfferSearches(originCode, destinationCode, departDate, returnDate, adultsNumber);
    }

    @PostMapping("/confirm")
    public FlightPrice confirmFlightPrice(@RequestBody() FlightOfferSearch flightOfferSearch) throws ResponseException {
        return AmadeusConnection.INSTANCE.confirmFlightPrice(flightOfferSearch);
    }

    @PostMapping("/traveler")
    public Traveler traveler(@RequestBody() JsonObject travelerInfo) {
        return DatabaseConnect.traveler(travelerInfo.get("data").getAsJsonObject());
    }

    @GetMapping("/order")
    public FlightOrder flightOrder(@RequestBody() JsonObject flightOrder) throws ResponseException {
        return AmadeusConnection.INSTANCE.flightOrder(flightOrder);
    }
}

