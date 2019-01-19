package ua.varguss.domain.panel.outside;

import lombok.NonNull;
import ua.varguss.domain.Elevator;

public class UpDownPanel extends AbstractOuterPanel {
    public UpDownPanel(@NonNull Elevator elevator) {
        super(elevator);
    }

    @Override
    public void callElevator(int desiredFloor) {
        // TODO: don't forget to implement
    }
}
