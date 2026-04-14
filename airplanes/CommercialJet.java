package airplanes;

public class CommercialJet extends Aircraft {
    private int passengerCapacity;

    public CommercialJet(String flightNumber, double requiredFuel, int requiredTurnaroundTime, int passengerCapacity) {
        super(flightNumber, requiredFuel, requiredTurnaroundTime);
        this.passengerCapacity = passengerCapacity;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }
    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
    
    @Override
    public void generateNewAircraft() {
        // Implementation for generating a new commercial jet
    }
    
}
