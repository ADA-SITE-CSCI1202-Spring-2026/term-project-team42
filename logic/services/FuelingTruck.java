package logic.services;

import airplanes.*;
import javax.naming.InsufficientResourcesException;
import logic.exceptions.InsufficientBudgetException;
import logic.managers.DepotManager;
import logic.managers.SupplyItem;

public class FuelingTruck implements IGroundService {
    private DepotManager depotManager;

    public FuelingTruck(DepotManager depotManager) {
        this.depotManager = depotManager;
    }
    
    @Override
    public void serviceFlight(FlightRequest request) throws InsufficientResourcesException, InsufficientBudgetException {
        if (!canProcess(request)) return;

        Aircraft aircraft = request.getAircraft();
    
        int fuelNeeded = aircraft.getRequiredFuel();

        depotManager.consumeResources(SupplyItem.FUEL, fuelNeeded);

        aircraft.isFueled(true);
    }

    @Override
    public boolean canProcess(FlightRequest request) {
        Aircraft aircraft = request.getAircraft();

        return aircraft.needsFuel() && !aircraft.isFueled();
    }

    @Override
    public String getServiceMessage(FlightRequest request) {
        return "Fueling Truck :: Providing fuel for flight " + request.getAircraft().getFlightNumber();
    }
    @Override
    public String getServiceType(FlightRequest request) {
        return "Refueling Service";
    }

    @Override
    public String getMainServiceResource(){
        return "Fuel";
    }
}