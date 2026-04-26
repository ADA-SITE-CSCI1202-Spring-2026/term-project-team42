package airplanes;

import java.util.Random;

public class CargoFreighter extends Aircraft {
    private int cargoCapacity;

    public CargoFreighter(String flightNumber, double requiredFuel, int requiredTurnaroundTime, int cargoCapacity) {
        super(flightNumber, requiredFuel, requiredTurnaroundTime);
        this.cargoCapacity = cargoCapacity;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }
    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public void generateAircraftModel() {
        String[] modelList = {"Boeing 747-8F", "Boeing 767-300F", "Boeing 777F", "Airbus A350F", "Airbus A330-200F", "Antonov An-124"};

        Random rand = new Random();
        int listIndex = rand.nextInt(modelList.length);

        setAircraftModel(modelList[listIndex]);
    }

    @Override
    public double calculateRevenue() {
        double revenue = cargoCapacity * 1000;
        return revenue;
    }

    @Override 
    public int getRequiredMeals() {
        return 0;
    }
    @Override
    public int getRequiredCarts() {
        return 0; 
    }

    @Override
    public String getLogDetails() {
        return String.format("INBOUND: %s [%s] - Cargo Capacity: %d tons", this.getFlightNumber(), this.getAircraftModel(), this.cargoCapacity);
    }
    @Override
    public java.awt.Color getThemeColor() {
        return new java.awt.Color(0, 0, 255); // blue
    }
}