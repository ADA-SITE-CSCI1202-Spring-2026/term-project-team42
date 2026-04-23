package logic;

<<<<<<< HEAD
=======
import airplanes.*;
>>>>>>> b09cddfb375bcd6236bbea053cba0578ab155878
import java.util.*;

public class FlightManager {
    private Queue<FlightRequest> queue = new LinkedList<>();
    private Random random = new Random();

<<<<<<< HEAD
    public void generateFlight() {
        Aircraft a;
        int type = random.nextInt(3);

        if (type == 0)
            a = new CommercialJet("CJ" + random.nextInt(1000), 500, 200);
        else if (type == 1)
            a = new CargoFreighter("CF" + random.nextInt(1000), 700, 50, 50000);
        else
            a = new PrivateCharter("PC" + random.nextInt(1000), 200, 20);

        queue.add(new FlightRequest(a));
=======
    public FlightRequest generateFlight() {
        Aircraft currentFlight;
        String id;
        int turnaround;
        double reqFuel;
        int capacity;

        int type = random.nextInt(3);
        switch (type) {
            case 0:
                id = "CARGO-" + (100 + random.nextInt(900));
                turnaround = 1 + random.nextInt(25);
                reqFuel = 3000.0 + turnaround * 355.0;
                capacity = 50 + random.nextInt(101);

                currentFlight = new CargoFreighter(id, reqFuel, turnaround, capacity);
                currentFlight.generateNewAircraft();
                break;
            case 1:
                id = "AZE-" + (100 + random.nextInt(900));
                int serviceLevel = random.nextInt(5) + 1;       
                int blockHours = random.nextInt(10) + 1;        
                double rate = 500.0 + (random.nextDouble() * 2000.0); 
                double position = 200.0 + random.nextInt(1000);
                double incidental = 50.0 + random.nextInt(500);
                turnaround = 1 + random.nextInt(25);
                reqFuel = 2000.0 + turnaround * 300.0;

                currentFlight = new PrivateCharter(id, reqFuel, turnaround, serviceLevel, blockHours, rate, position, incidental);
                currentFlight.generateNewAircraft();
                break;
            default:
                id = "SKW-" + (100 + random.nextInt(900));
                turnaround = 1 + random.nextInt(25);
                reqFuel = 2500.0 + turnaround * 255.0;
                capacity = 100 + random.nextInt(201);

                currentFlight = new CommercialJet(id, reqFuel, turnaround, capacity);
                currentFlight.generateNewAircraft();
                break; 
        }

        queue.add(new FlightRequest(currentFlight));
        return new FlightRequest(currentFlight);
>>>>>>> b09cddfb375bcd6236bbea053cba0578ab155878
    }

    public FlightRequest nextFlight() {
        return queue.poll();
    }

    public Queue<FlightRequest> getQueue() {
        return queue;
    }
}