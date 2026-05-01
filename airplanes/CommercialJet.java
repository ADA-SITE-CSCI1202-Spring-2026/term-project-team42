package airplanes;

import java.util.Random;

public class CommercialJet extends Aircraft {
    private int passengerCapacity;
    private double ticketPrice;

    public CommercialJet() {
        this(new Random());
    }
    private CommercialJet(Random random) {
        this(
            "SKW-" + (100 + random.nextInt(900)),   // id
            30 + random.nextInt(151),               // turnaround (mins)
            60 + random.nextInt(661),               // duration (mins)
            random
        );
    }
    private CommercialJet(String id, int turnaround, int duration, Random random) {
        this(
            id, 
            turnaround, 
            duration, 
            2500 + (duration / 60 * 255),           // fuel
            20 + random.nextInt(131)                // capacity
        );
    }

    public CommercialJet(String flightNumber, int requiredTurnaroundTime, int flightDuration, int requiredFuel, int passengerCapacity) {
        super(flightNumber, requiredTurnaroundTime, flightDuration, requiredFuel);
        this.passengerCapacity = passengerCapacity;
        this.ticketPrice = 10.0 + flightDuration/60.0 * 0.2 + requiredFuel * 0.05;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }
    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    @Override
    public void generateAircraftModel() {
        String[] modelList = {"Boeing 737","Airbus A320","Boeing 787 Dreamliner","Airbus A350","Embraer E190","Bombardier CRJ900"};

        Random rand = new Random();
        int listIndex = rand.nextInt(modelList.length);

        setAircraftModel(modelList[listIndex]);
    }
    @Override
    public double calculateRevenue() {
        double revenue = passengerCapacity * ticketPrice;
        return revenue;
    }

    @Override
    public int getRequiredMeals() {
        return passengerCapacity; 
    }
    @Override
    public int getRequiredCarts() {
        return  passengerCapacity * 2; 
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
        return String.format("COMMERCIAL JET REQUEST >> %s [%s] - Passenger Capacity: %d", this.getFlightNumber(), this.getAircraftModel(), this.passengerCapacity);
    }
    @Override
    public java.awt.Color getThemeColor() {
        return new java.awt.Color(0, 0, 255); //  blue
    }

    @Override
    public String toSaveString(){
        return String.format("CommercialJet|%s|%d|%d|%d|%d",
        getFlightNumber(), getRequiredTurnaroundTime(), 
        getFlightDuration(), getRequiredFuel(), this.passengerCapacity);
    }
}