package logic.controller;

import airplanes.*;
import gui.ControlTowerGUI;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import logic.actions.*;
import logic.exceptions.*;
import logic.managers.*;
import logic.persistence.*;
import logic.services.*;


public class ControlTowerController {


    private ControlTowerGUI view;
    private FlightManager flightManager;

    private Random rand = new Random();
    private Random randomTime = new Random();

    private DepotManager depotManager;;
    private List<IGroundService> groundService = new ArrayList<>();

    private FlightOperations operations;

    private fileIO SaveLoad;

    public ControlTowerController(ControlTowerGUI view) {
        this.view = view;
        this.flightManager = new FlightManager();
        this.depotManager = new DepotManager();
        this.operations = new FlightOperations(this.flightManager, this.depotManager, this.groundService);
        this.SaveLoad = new fileIO(flightManager, depotManager);

        initGroundServices();
        initHandlers();
        initSystem();
        startTimers();
        updateUI();
    }


    private void initGroundServices() {
        groundService.add(new CateringVan(depotManager)); 
        groundService.add(new FuelingTruck(depotManager));
        groundService.add(new BaggageHandler(depotManager));
    }

    private void initHandlers() {
        view.getClearBtn().addActionListener(action -> handleClearFlight());

        view.getBuyBtn().addActionListener(action -> handlePurchase());

        view.getSaveBtn().addActionListener(action -> {
            try {
                String IOFile = "airport_state.txt";
                SaveLoad.save(IOFile);
                view.log("SYSTEM: State saved to " + IOFile);                
            }catch (Exception e) {
            view.log("ERROR: Could not save state: " + e.getMessage(), Color.RED);
        }
        });

        view.getLoadBtn().addActionListener(action -> {
            try {
                String IOFile = "airport_state.txt";
                SaveLoad.loadState(IOFile);
                refreshQueueUI();
                updateUI(); 
                view.log("SYSTEM: Shift handoff complete. State loaded.");
            } catch (Exception e){
                view.log("ERROR: Load failed. File may be corrupted.", Color.RED);
                e.printStackTrace();
            }
        });
    }

    private void startTimers() {
        int timer = 3000;
        Timer arrivalTimer = new Timer(timer, e -> handleSpawnFlight());
        arrivalTimer.start();
        view.log("System Online. Monitoring...");
    }

    private void initSystem() {
        view.log("Welcome! This is the Skyways Airport Control Tower!");
    }

    private void updateUI() {
        view.updateStats(
            depotManager.getBudget(),
            depotManager.getSupply(SupplyItem.FUEL),
            depotManager.getSupply(SupplyItem.MEALS),
            depotManager.getSupply(SupplyItem.CARTS)
        );
    }
    private void refreshQueueUI() {
        DefaultListModel<FlightRequest> model = view.getQueueModel();
    
        model.clear();
    
        for (FlightRequest remainingRequest : flightManager.getQueue()) {
            model.addElement(remainingRequest);
        }
    }

    
    private void handleSpawnFlight() {
        FlightRequest newRequest = operations.spawnFlight();
        view.getQueueModel().addElement(newRequest);
        view.log("INBOUND: " + newRequest.toString() + " has entered holding pattern.\n", newRequest.getAircraft().getThemeColor());
    }

    private void handleClearFlight() {
        try {
            GroundServiceMessage result = operations.processClearFlight();

            String[] lines = result.getMessage().split("\n");
            for (String line : lines) {
                Color groundServiceTheme = result.getServicedRequest().getAircraft().getThemeColor();
                view.log(line, groundServiceTheme);
            }
        } catch (Exception e) {
            view.log(e.getMessage(), Color.RED);
        }
        refreshQueueUI();
        updateUI();
    }

    private void handlePurchase() {
        String selectedName = (String) view.getSupplyDrop().getSelectedItem();
        try {
            String successMessage = operations.purchaseSupply(selectedName);
            view.log(successMessage);
            updateUI();
        } catch (IllegalArgumentException e) {
            view.log("INPUT ERROR: " + e.getMessage(), Color.RED);
        } catch (InsufficientBudgetException e) {
            view.log("FINANCE ERROR: " + e.getMessage(), Color.RED);
        } catch (StockLimitExceededException e){
            view.log("DEPOT ERROR: " + e.getMessage(), Color.RED);
        }
    }   
}