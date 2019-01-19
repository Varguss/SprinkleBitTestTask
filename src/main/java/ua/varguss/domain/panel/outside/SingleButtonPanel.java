package ua.varguss.domain.panel.outside;

import lombok.NonNull;
import ua.varguss.domain.Call;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Floor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SingleButtonPanel extends AbstractOuterPanel {
    public SingleButtonPanel(@NonNull Elevator[] elevators, @NonNull Floor currentFloor) {
        super(elevators, currentFloor);
    }

    @Override
    public void callElevator(int floor) {
        callElevator(floor, new Elevator.Direction[] { Elevator.Direction.NONE });
    }
}
