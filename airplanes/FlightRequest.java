package airplanes;

public class FlightRequest {
    private Aircraft aircraft;
    
    public FlightRequest(Aircraft aircraft) {
        this.aircraft = aircraft;
        this.aircraft.generateNewAircraft();
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    @Override
    public String toString() {
        return aircraft.getFlightNumber() + " (" + aircraft.getAircraftModel() + ")";
    }
}