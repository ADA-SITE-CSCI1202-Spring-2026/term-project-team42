package airplanes;

import java.awt.Color;

public abstract class Aircraft {
    private String flightNumber;
    private int requiredFuel;
    private int requiredTurnaroundTime;
    private int flightDuration;
    private String aircraftModel;

    private boolean isCatered = false;
    private boolean isFueled = false;
    private boolean isBagged = false;

    protected Aircraft(String flightNumber, int requiredTurnaroundTime, int flightDuration, int requiredFuel) {
        this.flightNumber = flightNumber;
        this.requiredFuel = requiredFuel;
        this.requiredTurnaroundTime = requiredTurnaroundTime;
        this.flightDuration = flightDuration;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
    public int getRequiredFuel() {
        return requiredFuel;
    }
    public int getRequiredTurnaroundTime() {
        return requiredTurnaroundTime;
    }
    public int getFlightDuration() {
        return flightDuration;
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
    public void setRequiredFuel(int requiredFuel) {
        this.requiredFuel = requiredFuel;
    }
    public void setRequiredTurnaroundTime(int requiredTurnaroundTime) {
        this.requiredTurnaroundTime = requiredTurnaroundTime;
    }
    public void setFlightDuration(int flightDuration) {
        this.flightDuration = flightDuration;
    }
    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public void isCatered(boolean catered) {
        this.isCatered = catered;
    }
    public boolean isCatered() {
        return isCatered;
    }

    public void isFueled(boolean fueled) {
        this.isFueled = fueled;
    }
    public boolean isFueled() {
        return isFueled;
    }
    
    public void isBagged(boolean bagged) {
        this.isBagged = bagged;
    }
    public boolean isBagged() {
        return isBagged;
    }


    public abstract void generateAircraftModel();
    public abstract double calculateRevenue();

    public abstract int getRequiredMeals();
    public abstract int getRequiredCarts();

    public abstract boolean needsCatering();
    public abstract boolean needsFuel();
    public abstract boolean needsBaggage();

    public abstract String getLogDetails();
    public abstract Color getThemeColor();

<<<<<<< HEAD
    public int getRequiredMeals() {
        return 0;
    }
    
    public int getRequiredCarts() {
        return 0;
    }
=======
    public abstract String toSaveString();
>>>>>>> c6c97bdd63e05ca66bd0a50ad90fcd7c87ab7e3c
}