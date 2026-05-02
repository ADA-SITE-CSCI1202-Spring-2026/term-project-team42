package airplanes;

import java.util.Random;

public class CargoFreighter extends Aircraft {
    private int cargoCapacity;

    public CargoFreighter() {
        this(new Random());
    }
    private CargoFreighter(Random random) {
        this(
            "CARGO-" + (100 + random.nextInt(900)),     //id
            30+ random.nextInt(151),                    // turnaround (mins)
            300 + random.nextInt(601),                  // duration (mins)
            random
        );
    }
    private CargoFreighter(String id, int turnaround, int duration, Random random) {
        this(
            id, 
            turnaround, 
            duration, 
            2000 + duration/60 * 200,                   // fuel
            50 + random.nextInt(101)                    // capacity (unit)
        );
    }

    public CargoFreighter(String flightNumber, int requiredTurnaroundTime, int flightDuration, int requiredFuel, int cargoCapacity) {
        super(flightNumber, requiredTurnaroundTime, flightDuration, requiredFuel);
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
        double revenue = cargoCapacity * 20.0 + getRequiredFuel() * 0.1 + getFlightDuration() * 0.5;
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
    public boolean needsCatering() {
        return false;
    }
    @Override
    public boolean needsFuel() {
        return true;
    }
    @Override
    public boolean needsBaggage() {
        return false;
    }

    @Override
    public String getLogDetails() {
        return String.format("CARGO REQUEST >> %s [%s] - Cargo Capacity: %d units", this.getFlightNumber(), this.getAircraftModel(), this.cargoCapacity);
    }
    @Override
    public java.awt.Color getThemeColor() {
        return new java.awt.Color(70, 130, 180); // steel blue
    }

    @Override
    public String toSaveString(){
        return String.format("CargoFreighter|%s|%d|%d|%d|%d",
        getFlightNumber(), getRequiredTurnaroundTime(), 
        getFlightDuration(), getRequiredFuel(), this.cargoCapacity);
    }
}