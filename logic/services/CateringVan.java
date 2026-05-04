package logic.services;

import airplanes.*;
import javax.naming.InsufficientResourcesException;
import logic.exceptions.InsufficientBudgetException;
import logic.managers.DepotManager;
import logic.managers.SupplyItem;


public class CateringVan implements IGroundService {
    private DepotManager depotManager;

    public CateringVan(DepotManager depotManager) {
        this.depotManager = depotManager;
    }
    
    @Override
    public void serviceFlight(FlightRequest request) throws InsufficientResourcesException, InsufficientBudgetException {
        if (!canProcess(request)) return;

        Aircraft aircraft = request.getAircraft();
    
        int mealsNeeded = aircraft.getRequiredMeals(); 

        depotManager.consumeResources(SupplyItem.MEALS, mealsNeeded);
    
        aircraft.isCatered(true);
    }

    @Override
    public boolean canProcess(FlightRequest request) {
        Aircraft aircraft = request.getAircraft();

        return aircraft.needsCatering() && !aircraft.isCatered();
    }

    @Override
    public String getServiceMessage(FlightRequest request) {
        return "Catering Van :: Providing catering for flight " + request.getAircraft().getFlightNumber();
    }
    @Override
    public String getServiceType(FlightRequest request) {
        return "Catering Service";
    }

    @Override
    public String getMainServiceResource(){
        return "Meals";
    }
}