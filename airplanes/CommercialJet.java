package airplanes;

import java.util.Random;

public class CommercialJet extends Aircraft {
    private int passengerCapacity;
    private double ticketPrice;

    public CommercialJet(String flightNumber, double requiredFuel, int requiredTurnaroundTime, int passengerCapacity) {
        super(flightNumber, requiredFuel, requiredTurnaroundTime);
        this.passengerCapacity = passengerCapacity;
        this.ticketPrice = 100 + requiredTurnaroundTime*0.2 + requiredFuel*0.3;
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
    public void setTicketPrice(int ticketPrice) {
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
        return (int) Math.ceil(passengerCapacity / 50.0); 
    }

    @Override
    public String getLogDetails() {
        return String.format("INBOUND: %s [%s] - Passenger Capacity: %d", this.getFlightNumber(), this.getAircraftModel(), this.passengerCapacity);
    }
    @Override
    public java.awt.Color getThemeColor() {
        return new java.awt.Color(70, 130, 180); // steel blue
    }
}