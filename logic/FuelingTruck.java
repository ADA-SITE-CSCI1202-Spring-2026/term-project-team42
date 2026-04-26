package logic;

import airplanes.*;

public class FuelingTruck implements IGroundService {

    @Override
    public void serviceFlight(FlightRequest request) {
        if (!canProcess(request)) return;

        Aircraft aircraft = request.getAircraft();

        System.out.println("FuelingTruck: Refueling flight " + aircraft.getFlightNumber() + " with " + aircraft.getRequiredFuel() + "L of fuel.");
    }

    @Override
    public boolean canProcess(FlightRequest request) {
        return true;
    }
}