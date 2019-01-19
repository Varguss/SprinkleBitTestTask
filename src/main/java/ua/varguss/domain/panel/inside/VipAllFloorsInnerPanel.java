package ua.varguss.domain.panel.inside;

import lombok.NonNull;
import ua.varguss.domain.Elevator;

public class VipAllFloorsInnerPanel extends AllFloorsInnerPanel {

    public VipAllFloorsInnerPanel(@NonNull Elevator elevator) {
        super(elevator);
    }

    @Override
    public void selectFloor(int floor) {
        super.selectFloor(floor);
    }
}
