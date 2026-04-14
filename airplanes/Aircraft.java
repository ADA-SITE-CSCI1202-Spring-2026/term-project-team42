package airplanes;

public abstract class Aircraft {
    private String flightNumber;
    private double requiredFuel;
    private int requiredTurnaroundTime;
    private String aircraftModel;

    protected Aircraft(String flightNumber, double requiredFuel, int requiredTurnaroundTime) {
        this.flightNumber = flightNumber;
        this.requiredFuel = requiredFuel;
        this.requiredTurnaroundTime = requiredTurnaroundTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public double getRequiredFuel() {
        return requiredFuel;
    }
    public void setRequiredFuel(double requiredFuel) {
        this.requiredFuel = requiredFuel;
    }

    public int getRequiredTurnaroundTime() {
        return requiredTurnaroundTime;
    }
    public void setRequiredTurnaroundTime(int requiredTurnaroundTime) {
        this.requiredTurnaroundTime = requiredTurnaroundTime;
    }

     public String getAircraftModel() {
        if (this.aircraftModel == null) {
            return "Aircraft model is not available";
        }

        return this.aircraftModel;
    }
    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public abstract void generateNewAircraft();
}