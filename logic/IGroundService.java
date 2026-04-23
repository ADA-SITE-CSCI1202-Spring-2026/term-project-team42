package logic;

import airplanes.*;

public interface IGroundService {
    boolean canProcess(FlightRequest flightRequest);

    void serviceFlight(FlightRequest flightRequest);
}

