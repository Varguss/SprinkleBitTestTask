package ua.varguss.domain.panel.outside;

import lombok.NonNull;
import ua.varguss.domain.Call;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Floor;

public class UpDownPanel extends AbstractOuterPanel {
    public UpDownPanel(@NonNull Elevator[] elevators, @NonNull Floor currentFloor) {
        super(elevators, currentFloor);
    }

    @Override
    public void callElevator(int floor) {
        // TODO: don't forget to implement
    }
}
