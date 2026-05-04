package airplanes;

public class FlightRequest {
    private Aircraft aircraft;

    public FlightRequest(Aircraft aircraft) {
        this.aircraft = aircraft;
        this.aircraft.generateAircraftModel();
    }

    public Aircraft getAircraft() {
        return aircraft;
    }
    @Override
    public String toString() {
        return aircraft.getLogDetails();
    }
}