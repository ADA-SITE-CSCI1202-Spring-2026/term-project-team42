package logic.services;

import airplanes.*;
import javax.naming.InsufficientResourcesException;
import logic.exceptions.InsufficientBudgetException;

public interface IGroundService {
    boolean canProcess(FlightRequest flightRequest);

    void serviceFlight(FlightRequest flightRequest) throws InsufficientResourcesException, InsufficientBudgetException;

    String getServiceMessage(FlightRequest request);
    String getServiceType(FlightRequest request);

    String getMainServiceResource();
}