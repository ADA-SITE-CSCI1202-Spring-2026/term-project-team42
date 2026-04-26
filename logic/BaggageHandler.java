package logic;

import airplanes.*;

public class BaggageHandler implements IGroundService {

    @Override
    public void serviceFlight(FlightRequest request) {
        if (!canProcess(request)) return;

        Aircraft aircraft = request.getAircraft();

        System.out.println("BaggageHandler: Handling luggage for flight " + aircraft.getFlightNumber());
    }

    @Override
    public boolean canProcess(FlightRequest request) {
        Aircraft aircraft = request.getAircraft();

        return (aircraft instanceof CommercialJet || aircraft instanceof PrivateCharter);
    }
}