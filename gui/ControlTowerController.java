package gui;

import airplanes.*;
import java.util.Random;
import javax.swing.*;
import logic.*;


public class ControlTowerController {

    private ControlTowerGUI view;
    private Random rand = new Random();

    private DepotManager depotManager = new DepotManager();
    double budget = depotManager.getBudget();
    int fuel = depotManager.getSupply(SupplyItem.FUEL);
    int meals = depotManager.getSupply(SupplyItem.MEALS);
    int carts = depotManager.getSupply(SupplyItem.CARTS);

    public ControlTowerController(ControlTowerGUI view) {
        this.view = view;
        initHandlers();
        initSystem();
        startTimers();
        updateUI();
    }

    private void updateUI() {
        double budget = depotManager.getBudget();
        int fuel = depotManager.getSupply(SupplyItem.FUEL);
        int meals = depotManager.getSupply(SupplyItem.MEALS);
        int carts = depotManager.getSupply(SupplyItem.CARTS);

        view.updateStats(budget, fuel, meals, carts);
    }

    private void initHandlers() {
        view.getClearBtn().addActionListener(e -> handleClearFlight());

        view.getBuyBtn().addActionListener(e -> handlePurchase());
    }

    private void startTimers() {
        Timer arrivalTimer = new Timer(5000, e -> spawnFlight());
        arrivalTimer.start();
        log("System Online. Monitoring...");
    }

    private void initSystem() {
        log("System Online. This is the Skyways Airport Control Tower!");
    }

    private void spawnFlight() {
        Random rand = new Random();
        int type = rand.nextInt(3); // 0, 1, or 2

        switch (type) {
            case 0:
                spawnCargoFreighter();
                break;
            case 1:
                spawnPrivateCharter();
                break;
            default:
                spawnCommercialJet();
                break; 
        }
    }
    private void spawnCargoFreighter() {
        String id = "CARGO-" + (100 + rand.nextInt(900));

        int turnaround = 1 + rand.nextInt(25);
        double reqFuel = 3000.0 + turnaround * 355.0;
        int capacity = 50 + rand.nextInt(101);

        Aircraft newPlane = new CargoFreighter(id, reqFuel, turnaround, capacity);
    
        newPlane.generateNewAircraft();

        view.getQueueModel().addElement(newPlane); 
    
        log(String.format("INBOUND: %s [%s] - Cargo Capacity: %d tons", 
            newPlane.getFlightNumber(), 
            newPlane.getAircraftModel(), 
            capacity));
    }
    private void spawnPrivateCharter() {
        String id = "AZE-" + (100 + rand.nextInt(900));
    
        int serviceLevel = rand.nextInt(5) + 1;       
        int blockHours = rand.nextInt(10) + 1;        
        double rate = 500.0 + (rand.nextDouble() * 2000.0); 
        double position = 200.0 + rand.nextInt(1000);
        double incidental = 50.0 + rand.nextInt(500);
        int turnaround = 1 + rand.nextInt(25);
        double reqFuel = 2500.0 + turnaround * 300.0;
        Aircraft newPlane = new PrivateCharter(id, reqFuel, turnaround, serviceLevel, blockHours, rate, position, incidental);
    
        newPlane.generateNewAircraft();

        view.getQueueModel().addElement(newPlane); 
    
        log("CHARTER REQUEST: " + newPlane.getFlightNumber() + " (Level " + serviceLevel + ")");
    }
    private void spawnCommercialJet() {
        String id = "SKW-" + (100 + rand.nextInt(900));
    
        int turnaround = 1 + rand.nextInt(25);
        double reqFuel = 2000.0 + turnaround * 255.0;
        int capacity = 100 + rand.nextInt(201);

        Aircraft newPlane = new CommercialJet(id, reqFuel, turnaround, capacity);
    
        newPlane.generateNewAircraft();

        view.getQueueModel().addElement(newPlane); 
    
        log(String.format("INBOUND: %s [%s] - Passenger Capacity: %d", 
            newPlane.getFlightNumber(), 
            newPlane.getAircraftModel(), 
            capacity));
    }

   private void handleClearFlight() {
        DefaultListModel<Aircraft> queue = view.getQueueModel();
        String lackingResource = "";
        if (queue.isEmpty()) return;

        Aircraft plane = queue.getElementAt(0);

        if (depotManager.clearFlight(plane)) {
            queue.remove(0);
            log("SUCCESS: " + plane.getFlightNumber() + " cleared.");
        } else {
            if (depotManager.getSupply(SupplyItem.FUEL) < plane.getRequiredFuel()) {lackingResource = "Fuel";}
        //    else if (depotManager.getSupply(SupplyItem.MEALS) < []) {lackingResource = "Meals";}
           // else if (depotManager.getSupply(SupplyItem.CARTS) < []) {lackingResource = "Carts";}
           /* interface TODO */
            log("FAILED: Not enough " + lackingResource + ".");
        }
        
        updateUI();
    }

    private void handlePurchase() {
        String selectedItem = (String) view.getSupplyDrop().getSelectedItem();
        SupplyItem itemToBuy = null;
        int amount = 0;
        int cost = 0;

        switch (selectedItem) {
            case "Jet Fuel":
                itemToBuy = SupplyItem.FUEL;
                amount = 5000;
                cost = 2500;
                break;
            case "Meals":
                itemToBuy = SupplyItem.MEALS;
                amount = 100;
                cost = 500;
                break;
            case "Luggage Carts":
                itemToBuy = SupplyItem.CARTS;
                amount = 5;
                cost = 1200;
                break;
        }

        if (itemToBuy != null) {
            if (depotManager.getBudget() >= cost) {
                depotManager.restock(itemToBuy, amount, cost);
                log("ORDER FILLED: Received " + amount + " of " + selectedItem);
            } else {
                log("FINANCE ERROR: Insufficient budget for " + selectedItem);
            }
        }
        updateUI();
    }

    private void log(String message) {
        JTextArea logArea = view.getLogArea();
        logArea.append(message + "\n");
    }
}