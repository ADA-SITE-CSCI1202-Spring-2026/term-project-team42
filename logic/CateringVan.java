package logic;

import airplanes.Aircraft;

public class CateringVan implements IGroundService {

    @Override
    public void serviceFlight(Aircraft aircraft) {
        System.out.println("CateringVan: Providing meals for flight " + aircraft.getFlightNumber());
    }
}