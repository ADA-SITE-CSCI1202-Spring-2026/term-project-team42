package logic;

import airplanes.Aircraft;

public class FuelingTruck implements IGroundService {

    @Override
    public void serviceFlight(Aircraft aircraft) {
        double fuelNeeded = aircraft.getRequiredFuel();

        System.out.println("FuelingTruck: Refueling flight " + aircraft.getFlightNumber() + " with " + fuelNeeded + "L of fuel.");
    }
}