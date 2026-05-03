package logic.persistence;

import java.io.*;
import java.util.*;

import airplanes.*;
import logic.managers.*;

public class fileIO {
    private  FlightManager flightManager;
    private  DepotManager depotManager;

    public fileIO (FlightManager flightManager, DepotManager depotManager) {
        this.flightManager = flightManager;
        this.depotManager = depotManager;
    }

    public void save(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("BUDGET:" + depotManager.getBudget());

            StringBuilder resLine = new StringBuilder("RESOURCES:");
            depotManager.getSupplies().forEach((item, qty) -> {
                resLine.append(item.getDisplayName()).append("=").append(qty).append(",");
            });
            writer.println(resLine.toString());

            writer.println("QUEUE_START");
            for (FlightRequest req : flightManager.getQueue()) {
                writer.println(req.getAircraft().toSaveString());
            }
            writer.println("QUEUE_END");
        }
    }
    
    public void loadState(String filename) throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            flightManager.getQueue().clear(); 

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("BUDGET:")) {
                    double b = Double.parseDouble(line.split(":")[1]);
                    depotManager.setBudget(b);
                } 
                else if (line.startsWith("RESOURCES:")) {
                    String data = line.substring(10); 
                    String[] entries = data.split(",");
                    for (String entry : entries) {
                        if (entry.trim().isEmpty()) continue;
                        if (entry.contains("=")) {
                            String[] pair = entry.split("=");
                            String nameInFile = pair[0];
                            int quantity = Integer.parseInt(pair[1]);

                            SupplyItem item = depotManager.findItemByDisplayName(nameInFile);
                            if (item != null) {
                                depotManager.getSupplies().put(item, quantity);
                            }
                        }
                    }      
                } 
                else if (line.equals("QUEUE_START")) {
                    while ((line = reader.readLine()) != null && !line.equals("QUEUE_END")) {
                        Aircraft restored = createAircraftByType(line);
                        if (restored != null) {
                            flightManager.getQueue().add(new FlightRequest(restored));
                        }
                    }
                }
            }
        }
    }
    private Aircraft createAircraftByType(String line) {
        String[] data = line.split("\\|");
        if (data.length < 5) return null;

        String type = data[0];
        String id = data[1];
        int turnaround = Integer.parseInt(data[2]);
        int duration = Integer.parseInt(data[3]);
        int fuel = Integer.parseInt(data[4]);

        return switch (type) {
            case "CargoFreighter" -> {
                int capacity = Integer.parseInt(data[5]);
                yield new CargoFreighter(id, turnaround, duration, fuel, capacity);
            }
            case "CommercialJet" -> {
                int passengers = Integer.parseInt(data[5]);
                yield new CommercialJet(id, turnaround, duration, fuel, passengers);
            }
            case "PrivateCharter" -> {
                int service = Integer.parseInt(data[5]);
                double rate = Double.parseDouble(data[6]);
                double posCost = Double.parseDouble(data[7]);
                double incCost = Double.parseDouble(data[8]);
                yield new PrivateCharter(id, turnaround, duration, fuel, service, rate, posCost, incCost);
            }
            default -> throw new IllegalArgumentException("Unknown aircraft type: " + type);
        };
    }
}