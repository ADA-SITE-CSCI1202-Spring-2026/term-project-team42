package airplanes;

import java.util.Random;

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
        String[] modelList = {"Boeing 737","Airbus A320","Boeing 787 Dreamliner","Airbus A350","Embraer E190","Bombardier CRJ900"};

        Random rand = new Random();
        int listIndex = rand.nextInt(modelList.length);

        setAircraftModel(modelList[listIndex]);
    }
}