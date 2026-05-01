package logic;

import airplanes.Aircraft;

public class BaggageHandler implements IGroundService {

    @Override
    public void serviceFlight(Aircraft aircraft) {

        System.out.println("BaggageHandler: Handling luggage for flight " + aircraft.getFlightNumber());
    }
}
