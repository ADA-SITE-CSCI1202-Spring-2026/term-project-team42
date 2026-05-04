package logic.managers;

import airplanes.*;
import java.util.*;

public class FlightManager {
    private LinkedList<FlightRequest> queue = new LinkedList<>();
    private Random random = new Random();

    public FlightRequest generateFlight() {
        Aircraft currentFlight;

        int type = random.nextInt(3);
        switch (type) {
            case 0:
                currentFlight = new CargoFreighter();
                break;
            case 1:
                currentFlight = new PrivateCharter();
                break;
            default:
                currentFlight = new CommercialJet();
                break; 
        }
        currentFlight.generateAircraftModel();
        
        FlightRequest request = new FlightRequest(currentFlight);
        queue.add(request);
    
        return request;
    }

    public FlightRequest nextFlight() {
        return queue.poll();
    }

    public LinkedList<FlightRequest> getQueue() {
        return queue;
    }
}