package airplanes;

import java.awt.Color;

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

    public double getRequiredFuel() {
        return requiredFuel;
    }

    public int getRequiredTurnaroundTime() {
        return requiredTurnaroundTime;
    }

    public String getAircraftModel() {
        if (this.aircraftModel == null) {
            return "Aircraft model is not available";
        }

        return this.aircraftModel;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setRequiredFuel(double requiredFuel) {
        this.requiredFuel = requiredFuel;
    }

    public void setRequiredTurnaroundTime(int requiredTurnaroundTime) {
        this.requiredTurnaroundTime = requiredTurnaroundTime;
    }
    
    
    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }


    public abstract void generateAircraftModel();
    public abstract double calculateRevenue();

    public abstract String getLogDetails();
    public abstract Color getThemeColor();

    public int getRequiredMeals() {
        return 0;
    }
    
    public int getRequiredCarts() {
        return 0;
    }
}