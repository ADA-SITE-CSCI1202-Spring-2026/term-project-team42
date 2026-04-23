package logic;

import airplanes.*;

public interface IGroundService {
    boolean canProcess(Aircraft aircraft);

    void serviceFlight(Aircraft aircraft);
}
