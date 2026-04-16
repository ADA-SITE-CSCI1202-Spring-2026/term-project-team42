package airplanes;

import java.util.Random;

public class PrivateCharter extends Aircraft {
    private int serviceLevel;

    public PrivateCharter(String flightNumber, double requiredFuel, int requiredTurnaroundTime, int serviceLevel) {
        super(flightNumber, requiredFuel, requiredTurnaroundTime);
        this.serviceLevel = serviceLevel;
    }

    public int getServiceLevel() {
        return serviceLevel;
    }
    public void setServiceLevel(int serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    @Override
    public void generateNewAircraft() {
        String[] modelList = {"Embraer Phenom 300,", "Bombardier Challenger 350", "Gulfstream G650ER", "Embraer Praetor 500", "Cessna Citation XLS+", "Dassault Falcon 2000LXS"};

        Random rand = new Random();
        int listIndex = rand.nextInt(modelList.length);

        setAircraftModel(modelList[listIndex]);
    }
}