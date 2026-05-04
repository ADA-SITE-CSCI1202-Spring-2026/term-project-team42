package logic.services;

import airplanes.*;
import javax.naming.InsufficientResourcesException;
import logic.exceptions.InsufficientBudgetException;
import logic.managers.DepotManager;
import logic.managers.SupplyItem;

public class BaggageHandler implements IGroundService {
    private DepotManager depotManager;

    public BaggageHandler(DepotManager depotManager) {
        this.depotManager = depotManager;
    }
    
    @Override
    public void serviceFlight(FlightRequest request) throws InsufficientResourcesException, InsufficientBudgetException {
        if (!canProcess(request)) return;

        Aircraft aircraft = request.getAircraft();
    
        int cartsNeeded = aircraft.getRequiredCarts(); 

        depotManager.consumeResources(SupplyItem.CARTS, cartsNeeded);
    
        aircraft.isBagged(true);
    }

    @Override
    public boolean canProcess(FlightRequest request) {
        Aircraft aircraft = request.getAircraft();

        return aircraft.needsBaggage() && !aircraft.isBagged();
    }

    @Override
    public String getServiceMessage(FlightRequest request) {
        return "Baggage Handler :: Handling baggage for flight " + request.getAircraft().getFlightNumber();
    }

    @Override
    public String getServiceType(FlightRequest request) {
        return "Baggage Handling";
    }

    @Override
    public String getMainServiceResource(){
        return "Carts";
    }
}