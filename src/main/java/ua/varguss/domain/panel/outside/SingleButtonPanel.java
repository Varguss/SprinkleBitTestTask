package ua.varguss.domain.panel.outside;

import lombok.NonNull;
import ua.varguss.domain.Call;
import ua.varguss.domain.Elevator;

public class SingleButtonPanel extends AbstractOuterPanel {
    public SingleButtonPanel(@NonNull Elevator elevator) {
        super(elevator);
    }

    @Override
    public void callElevator(Call call) {
        // TODO: don't forget to implement
    }
}
