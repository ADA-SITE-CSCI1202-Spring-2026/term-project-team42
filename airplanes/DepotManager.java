package airplanes;

import java.util.HashMap;

public class DepotManager {

    private HashMap<SupplyItem, Integer> supplies;
    private int budget;

    public DepotManager() {
        supplies = new HashMap<>();
        budget = 50000;

        supplies.put(SupplyItem.FUEL, 1800);
        supplies.put(SupplyItem.MEALS, 300);
        supplies.put(SupplyItem.CARTS, 15);
    }

    public int getSupply(SupplyItem item) {
        return supplies.getOrDefault(item, 0);
    }

    public int getBudget() {
        return budget;
    }

    public boolean canClear(Aircraft aircraft) {

        if (getSupply(SupplyItem.FUEL) < aircraft.getRequiredFuel()) {
            return false;
        }

        int mealsNeeded = estimateMeals(aircraft);

        if (getSupply(SupplyItem.MEALS) < mealsNeeded) {
            return false;
        }

        return true;
    }

    public boolean clearFlight(Aircraft aircraft) {

        if (!canClear(aircraft)) {
            return false;
        }

        int fuel = (int) aircraft.getRequiredFuel();
        int meals = estimateMeals(aircraft);
        int carts = estimateCarts(aircraft);

        supplies.put(SupplyItem.FUEL, getSupply(SupplyItem.FUEL) - fuel);
        supplies.put(SupplyItem.MEALS, getSupply(SupplyItem.MEALS) - meals);
        supplies.put(SupplyItem.CARTS, getSupply(SupplyItem.CARTS) - carts);

        budget += aircraftRevenue(aircraft);

        return true;
    }

    private int estimateMeals(Aircraft aircraft) {
        if (aircraft instanceof CommercialJet) {
            return ((CommercialJet) aircraft).getPassengerCapacity() / 2;
        } else if (aircraft instanceof CargoFreighter) {
            return 20;
        }
        return 10; 
    }

    private int estimateCarts(Aircraft aircraft) {
        if (aircraft instanceof CargoFreighter) {
            return 5;
        }
        return 2;
    }

    private int aircraftRevenue(Aircraft aircraft) {
        if (aircraft instanceof CommercialJet) {
            return 5000;
        } else if (aircraft instanceof CargoFreighter) {
            return 7000;
        }
        return 3000;
    }

    public void restock(SupplyItem item, int amount, int cost) {
        if (budget < cost) return;

        supplies.put(item, getSupply(item) + amount);
        budget -= cost;
    }
}