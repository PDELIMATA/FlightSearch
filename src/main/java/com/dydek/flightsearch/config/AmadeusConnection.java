package com.dydek.flightsearch.config;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referenceData.Locations;
import com.amadeus.resources.*;
import com.google.gson.JsonObject;

public enum AmadeusConnection {

    INSTANCE;
    private final Amadeus amadeus;

    AmadeusConnection() {
        this.amadeus = Amadeus
                .builder("AMADEUS_CLIENT_ID", "AMADEUS_CLIENT_SECRET")
                .build();
    }

    public Location[] airportLocation(String keyword) throws ResponseException {
        return amadeus.referenceData.locations.get(Params
                .with("keyword", keyword)
                .and("subType", Locations.AIRPORT));
    }

    public FlightOfferSearch[] flightOfferSearches(String origin, String destination, String departDate, String returnDate, String adults) throws ResponseException {
        return amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", origin)
                        .and("destinationLocationCode", destination)
                        .and("departureDate", departDate)
                        .and("returnDate", returnDate)
                        .and("adults", adults)
                        .and("max", 5));
    }


    public FlightPrice confirmFlightPrice(FlightOfferSearch offer) throws ResponseException {
        return amadeus.shopping.flightOffersSearch.pricing.post(offer);
    }

    public FlightOrder flightOrder(JsonObject flightOrder) throws ResponseException {
        return amadeus.booking.flightOrders.post(flightOrder);
    }
}
