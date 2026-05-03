package logic.actions;


import airplanes.FlightRequest;

public class GroundServiceMessage {
    private final String message;
    private FlightRequest flightRequest;

    public GroundServiceMessage(String message, FlightRequest flightRequest) {
        this.message = message;
        this.flightRequest = flightRequest;
    }

    public String getMessage() { return message; }
    public FlightRequest getServicedRequest() { return flightRequest; }
}