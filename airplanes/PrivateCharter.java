package airplanes;

public class PrivateCharter extends Aircraft {
    public PrivateCharter(String flightNumber, double requiredFuel, int requiredTurnaroundTime) {
        super(flightNumber, requiredFuel, requiredTurnaroundTime);
    }

    @Override
    public void generateNewAircraft() {
        // Implementation for generating a new private charter
    }
    
}
