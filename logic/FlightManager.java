package logic;

import java.util.*;

public class FlightManager {
    private Queue<FlightRequest> queue = new LinkedList<>();
    private Random random = new Random();

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
    }

    public FlightRequest nextFlight() {
        return queue.poll();
    }

    public Queue<FlightRequest> getQueue() {
        return queue;
    }
}