package ua.varguss.domain.panel.outside;

import lombok.NonNull;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Floor;

public class SingleButtonPanel extends AbstractOuterPanel {
    public SingleButtonPanel(@NonNull Elevator[] elevators, @NonNull Floor currentFloor) {
        super(elevators, currentFloor);
    }

    @Override
    public boolean callElevator(int floor) {
        return callElevator(floor, new Elevator.Direction[] { Elevator.Direction.NONE });
    }
}
