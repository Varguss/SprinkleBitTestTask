package ua.varguss.domain.panel.outside;

import lombok.NonNull;
import ua.varguss.domain.Call;
import ua.varguss.domain.Elevator;

public class UpDownPanel extends AbstractOuterPanel {
    public UpDownPanel(@NonNull Elevator[] elevators) {
        super(elevators);
    }

    @Override
    public void callElevator(int floor) {
        // TODO: don't forget to implement
    }
}
