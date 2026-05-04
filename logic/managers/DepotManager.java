package logic.managers;

import airplanes.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.naming.InsufficientResourcesException;
import logic.exceptions.*;

public class DepotManager {

    private HashMap<SupplyItem, Integer> supplies;
    private double budget;

    public DepotManager() {
        supplies = new HashMap<>();
        budget = 50000.0;

        supplies.put(SupplyItem.FUEL, 5000);
        supplies.put(SupplyItem.MEALS, 300);
        supplies.put(SupplyItem.CARTS, 100);
    }

    public int getSupply(SupplyItem item) {
        return supplies.getOrDefault(item, 0);
    }

    public double getBudget() {
        return budget;
    }
    public void setBudget(double budget){
        this.budget = budget;
    }

    public HashMap<SupplyItem, Integer> getSupplies() {
        return this.supplies; 
    }
    public SupplyItem findItemByDisplayName(String name) {
        for (SupplyItem item : supplies.keySet()) {
            if (item.getDisplayName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public List<String> checkResources(Aircraft aircraft) {
        List<String> missing = new ArrayList<>();

        if (getSupply(SupplyItem.FUEL) < aircraft.getRequiredFuel()) {
            missing.add("Fuel");
        }

        if (getSupply(SupplyItem.MEALS) < aircraft.getRequiredMeals()) {
            missing.add("Meals");
        }

        if (getSupply(SupplyItem.CARTS) < aircraft.getRequiredCarts()) {
            missing.add("Carts");
        }

        return missing;
    }

    public boolean canClear(Aircraft aircraft) {
        return checkResources(aircraft).isEmpty();
    }

    public boolean clearFlight(Aircraft aircraft) {

        if (!canClear(aircraft)) {
            return false;
        }

        int fuel = (int) aircraft.getRequiredFuel();
        int meals = aircraft.getRequiredMeals();
        int carts = aircraft.getRequiredCarts();

        supplies.put(SupplyItem.FUEL, getSupply(SupplyItem.FUEL) - fuel);
        supplies.put(SupplyItem.MEALS, getSupply(SupplyItem.MEALS) - meals);
        supplies.put(SupplyItem.CARTS, getSupply(SupplyItem.CARTS) - carts);

        budget += aircraft.calculateRevenue();

        return true;
    }

    public void consumeResources(SupplyItem item, int amount) 
        throws InsufficientResourcesException, InsufficientBudgetException {
    
        if (getSupply(item) < amount) {
            throw new InsufficientResourcesException("Not enough " + item.getDisplayName() + " in stock! Increase the supply of " + item.getDisplayName() + " by " + (amount - getSupply(item)) + " units.");
        }
 
        int current = supplies.getOrDefault(item, 0);
        supplies.put(item, current - amount);
    }
    
    public void spendBudget(double cost) throws InsufficientBudgetException {
        if (budget < cost) {
            double missingAmount = cost - getBudget();
            throw new InsufficientBudgetException(String.format("Increase the budget by %.2f$.", missingAmount));
        }
        budget -= cost;
    }
    public void increaseBudget(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to increase must be positive.");
        }
        budget += amount;
    }

    public void restock(SupplyItem item) throws InsufficientBudgetException, StockLimitExceededException{
        if(item == null) {
            throw new IllegalArgumentException("The type of supply item is not provided. Choose an option from the dropdown menu!");
        }

        int amountToAdd = item.getDefaultAmount();
        int currentStock = getSupply(item);

        if (currentStock + amountToAdd > item.getMaxCapacity()) {
            throw new StockLimitExceededException("Cannot add " + amountToAdd + " units to " + item.getDisplayName() +
                                " Current Stock: " + currentStock + "/" + item.getMaxCapacity());
        }

        double cost = item.getDefaultCost();
        spendBudget(cost);
        supplies.put(item, getSupply(item) + amountToAdd);

    }
}