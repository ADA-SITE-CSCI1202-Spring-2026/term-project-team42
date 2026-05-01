package airplanes;

import java.util.Random;

public class PrivateCharter extends Aircraft {
    private int serviceLevel;
    private double hourlyRate;
    private double positioningCost;
    private double incidentalCost;

    public PrivateCharter() {
        this(new Random());
    }
    private PrivateCharter(Random random) {
        this(
            "AZE-" + (100 + random.nextInt(900)),   // id
            30 + random.nextInt(151),               // turnaround (mins)
            120 + random.nextInt(121),              // duration (mins)
            random
        );
    }
    private PrivateCharter(String id, int turnaround, int duration, Random random) {
        this(
            id, 
            turnaround, 
            duration, 
            random,
            random.nextInt(5) + 1                   // serviceLevel
        );
    }
    private PrivateCharter(String id, int turnaround, int duration, Random random, int serviceLevel) {
        this(
            id, 
            turnaround, 
            duration, 
            2000 + (duration / 60 * 300),          // fuel
            serviceLevel,
            50.0 + (serviceLevel * 50.0),          // hourly rate
            15.0 + random.nextDouble(101.0),       // positioning cost
            10.0 + random.nextDouble(101.0)        // incidental cost
        );
    }

    public PrivateCharter(String flightNumber, int requiredTurnaroundTime, int flightDuration, int requiredFuel, int serviceLevel, double hourlyRate, double positioningCost, double incidentalCost) {
        super(flightNumber, requiredTurnaroundTime, flightDuration, requiredFuel);
        this.serviceLevel = serviceLevel;
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
        double revenue = 1.0 * (serviceLevel*50 + this.getFlightDuration()/60 * hourlyRate + positioningCost + incidentalCost);
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
    public boolean needsCatering() {
        return true;
    }
    @Override
    public boolean needsFuel() {
        return true;
    }
    @Override
    public boolean needsBaggage() {
        return true;
    }

    @Override
    public String getLogDetails() {
        return "CHARTER REQUEST >> " + this.getFlightNumber() + " (Level " + this.serviceLevel + ")";
    }
    @Override
    public java.awt.Color getThemeColor() {
        return new java.awt.Color(255, 215, 0); // gold
    }

    @Override
    public String toSaveString() {
        return String.format(java.util.Locale.US, "PrivateCharter|%s|%d|%d|%d|%d|%.2f|%.2f|%.2f",
            getFlightNumber(), 
            getRequiredTurnaroundTime(), 
            getFlightDuration(), 
            getRequiredFuel(), 
            this.serviceLevel, 
            this.hourlyRate, 
            this.positioningCost, 
            this.incidentalCost
        );
    }
}