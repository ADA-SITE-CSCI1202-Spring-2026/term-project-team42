package airplanes;

import java.util.Random;

public class PrivateCharter extends Aircraft {
    private int serviceLevel;
    private int blockHours;
    private double hourlyRate;
    private double positioningCost;
    private double incidentalCost;


    public PrivateCharter(String flightNumber, double requiredFuel, int requiredTurnaroundTime, int serviceLevel, int blockHours, double hourlyRate, double positioningCost, double incidentalCost) {
        super(flightNumber, requiredFuel, requiredTurnaroundTime);
        this.serviceLevel = serviceLevel;
        this.blockHours = blockHours;
        this.hourlyRate = hourlyRate;
        this.positioningCost = positioningCost;
        this.incidentalCost = incidentalCost;
    }

    public int getServiceLevel() {
        return serviceLevel;
    }
    public void setServiceLevel(int serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public int getBlockHours() {
        return blockHours;
    }
    public void setBlockHours(int blockHours) {
        this.blockHours = blockHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }
    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getPositioningCost() {
        return positioningCost;
    }
    public void setPositioningCost(double positioningCost) {
        this.positioningCost = positioningCost;
    }

    public double getIncidentalCost() {
        return incidentalCost;
    }
    public void setIncidentalCost(double incidentalCost) {
        this.incidentalCost = incidentalCost;
    }

    @Override
    public void generateAircraftModel() {
        String[] modelList = {"Embraer Phenom 300,", "Bombardier Challenger 350", "Gulfstream G650ER", "Embraer Praetor 500", "Cessna Citation XLS+", "Dassault Falcon 2000LXS"};

        Random rand = new Random();
        int listIndex = rand.nextInt(modelList.length);

        setAircraftModel(modelList[listIndex]);
    }

    @Override
    public double calculateRevenue() {
        double revenue = serviceLevel*500 + blockHours * hourlyRate + positioningCost + incidentalCost;
        return revenue;
    }

    @Override
    public int getRequiredMeals() {
        return serviceLevel * 4;
    }
    @Override
    public int getRequiredCarts() {
        return serviceLevel * 3;
    }

    @Override
    public String getLogDetails() {
        return "CHARTER REQUEST: " + this.getFlightNumber() + " (Level " + this.serviceLevel + ")";
    }
    @Override
    public java.awt.Color getThemeColor() {
        return new java.awt.Color(255, 215, 0); // gold
    }
}