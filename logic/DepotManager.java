package logic;

import airplanes.*;
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

    public String checkResources(Aircraft aircraft) {

        if (getSupply(SupplyItem.FUEL) < aircraft.getRequiredFuel()) {
            return "FUEL";
        }

        int mealsNeeded = aircraft.getRequiredMeals();
        if (getSupply(SupplyItem.MEALS) < mealsNeeded) {
            return "MEALS";
        }

        int cartsNeeded = aircraft.getRequiredCarts();
        if (getSupply(SupplyItem.CARTS) < cartsNeeded) {
            return "CARTS";
        }

        return "OKAY";
    }

    public boolean canClear(Aircraft aircraft) {
        return checkResources(aircraft).equals("OKAY");
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

    public boolean restock(SupplyItem item, int amount, int cost) {

        if (budget < cost) {
            return false;
        }

        supplies.put(item, getSupply(item) + amount);
        budget -= cost;

        return true;
    }
}