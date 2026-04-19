package gui;

import airplanes.*;
import java.util.Random;
import javax.swing.*;

public class ControlTowerController {

    private ControlTowerGUI view;
    private Random rand = new Random();

    private double currentBudget = 50000;
    private int currentFuel = 5000;
    private int currentMeals = 400;    
    private int currentCarts = 15;

    public ControlTowerController(ControlTowerGUI view) {
        this.view = view;
        initHandlers();
        initSystem();
        startTimers();
    }

    private void initHandlers() {
        view.getClearBtn().addActionListener(e -> handleClearFlight());

        view.getBuyBtn().addActionListener(e -> handlePurchase());
    }

    private void startTimers() {
        // Lambda for the Timer: Spawns a flight every 5 seconds
        Timer arrivalTimer = new Timer(5000, e -> spawnFlight());
        arrivalTimer.start();
        log("System Online. Monitoring...");
    }

    private void initSystem() {
        log("System Online. Monitoring...");
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
    
        double fuel = 3000.0 + (rand.nextDouble() * 7000.0);
        int turnaround = 60 + rand.nextInt(121);
        int capacity = 50 + rand.nextInt(101);

        Aircraft newPlane = new CargoFreighter(id, fuel, turnaround, capacity);
    
        newPlane.generateNewAircraft();

  //      view.getQueueModel().addElement(newPlane.toString()); 
    
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

        Aircraft newPlane = new PrivateCharter(id, 800.0, 30, serviceLevel, blockHours, rate, position, incidental);
    
        newPlane.generateNewAircraft();

 //       view.getQueueModel().addElement(newPlane.toString()); 
    
        log("CHARTER REQUEST: " + newPlane.getFlightNumber() + " (Level " + serviceLevel + ")");
    }
    private void spawnCommercialJet() {
        String id = "SKW-" + (100 + rand.nextInt(900));
    
        double fuel = 2000.0 + (rand.nextDouble() * 5000.0);
        int turnaround = 30 + rand.nextInt(61);
        int capacity = 100 + rand.nextInt(201);

        Aircraft newPlane = new CommercialJet(id, fuel, turnaround, capacity);
    
        newPlane.generateNewAircraft();

    //    view.getQueueModel().addElement(newPlane.toString()); 
    
        log(String.format("INBOUND: %s [%s] - Passenger Capacity: %d", 
            newPlane.getFlightNumber(), 
            newPlane.getAircraftModel(), 
            capacity));
    }

   private void handleClearFlight() {
        DefaultListModel<Aircraft> queue = (DefaultListModel<Aircraft>) view.getQueueModel();

        if (queue.isEmpty()) {
            log("WARNING: No flights in holding pattern.");
            return;
        }

        Aircraft plane = queue.remove(0);

        double earned = plane.calculateRevenue();
        currentBudget += earned;

        log(String.format("SUCCESS: %s [%s] landed. Revenue: +$%.2f", 
            plane.getFlightNumber(), plane.getAircraftModel(), earned));
    
 //       view.updateStats(currentBudget, currentFuel); 
    }

    private void handlePurchase() {
        String selected = (String) view.getSupplyDrop().getSelectedItem();
        if (selected == null) return;

        double cost = 0;

        switch (selected) {
            case "Jet Fuel":
                cost = 2000;
                if (currentBudget >= cost) {
                    currentFuel += 1000;
                    currentBudget -= cost;
                    log("PURCHASE: +1000L Jet Fuel.");
                }
                break;

            case "Meals":
                cost = 500;
                if (currentBudget >= cost) {
                    currentMeals += 200;
                    currentBudget -= cost;
                    log("PURCHASE: +200 Meals stocked.");
                }
                break;

            case "Luggage Carts":
                cost = 1500;
                if (currentBudget >= cost) {
                    currentCarts += 5;
                    currentBudget -= cost;
                    log("PURCHASE: +5 Luggage Carts deployed.");
                }
                break;
        }

        if (currentBudget < cost) {
            log("ERROR: Insufficient funds for " + selected);
        }
    
   //     view.updateStats(currentBudget, currentFuel, currentMeals, currentCarts);
    }

    private void log(String message) {
        JTextArea logArea = view.getLogArea();
        logArea.append(message + "\n");
    }
}