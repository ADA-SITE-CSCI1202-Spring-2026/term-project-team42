package logic.actions;

import airplanes.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import logic.exceptions.*;
import logic.managers.*;
import logic.services.*;


public class FlightOperations {
    private final FlightManager flightManager;
    private final DepotManager depotManager;
    private final List<IGroundService> groundService;

    public FlightOperations(FlightManager flightManager, DepotManager depotManager, List<IGroundService> groundService) {
        this.flightManager = flightManager;
        this.depotManager = depotManager;
        this.groundService = groundService;
    }

    public GroundServiceMessage processClearFlight() throws Exception {
        FlightRequest currentRequest = flightManager.getQueue().poll();

        if (currentRequest == null) {
            throw new Exception("SYSTEM ERROR: No aircraft currently in holding pattern.");
        }

        String groundServiceSummary;
        
        Aircraft currentAircraft = currentRequest.getAircraft();

        List<String> serveLogs = new ArrayList<>();
        List<String> errLogs = new ArrayList<>();
        List<String> missingResources = depotManager.checkResources(currentAircraft);

        if(depotManager.canClear(currentAircraft)){
            for (IGroundService service : groundService) {
                if (service.canProcess(currentRequest)) {
                    service.serviceFlight(currentRequest);
                    serveLogs.add("GROUND SERVICE SUCCESSFUL: " + service.getServiceMessage(currentRequest));
                }
            }
            double revenue = currentAircraft.calculateRevenue();
            depotManager.increaseBudget(revenue);
    
            groundServiceSummary =  String.join("\n", serveLogs) + 
                                            "\nCLEARED: " + currentAircraft.getFlightNumber() + " cleared for takeoff." +
                                            " >> Revenue earned: $" + String.format("%.2f", revenue);
        } else{
            for (IGroundService service : groundService) {
                if (service.canProcess(currentRequest) && missingResources.contains(service.getMainServiceResource())) {
                    try {
                        service.serviceFlight(currentRequest);
                    } catch (Exception e) {    
                        errLogs.add("GROUND SERVICE DENIED: " + service.getServiceType(currentRequest) + " " + currentAircraft.getFlightNumber() + " >> " + e.getMessage());
                    }
                }
            }
            ((LinkedList<FlightRequest>) flightManager.getQueue()).addFirst(currentRequest);
            throw new Exception(String.join("\n", errLogs));
        }
    
        return new GroundServiceMessage(groundServiceSummary, currentRequest);
    }

    public String purchaseSupply(String supplyName) throws InsufficientBudgetException, StockLimitExceededException {
        SupplyItem supplyToBuy = SupplyItem.fromString(supplyName);

        try {
            depotManager.restock(supplyToBuy);
        
            int amountToAdd = supplyToBuy.getDefaultAmount();
            int cost = supplyToBuy.getDefaultCost();
            String supplyName_name = supplyToBuy.getDisplayName();
            return ("ORDER FILLED: Received " + amountToAdd + " units of " + supplyName_name + ". Cost >> " + cost);
        } catch (InsufficientBudgetException e) {
            throw new InsufficientBudgetException("Insufficient budget for " + 
                supplyToBuy.getDisplayName() + " >> " + e.getMessage());
        } catch (StockLimitExceededException e){
            throw new StockLimitExceededException(e.getMessage());
        }

    }

    public FlightRequest spawnFlight() {
        return flightManager.generateFlight();
    }
}