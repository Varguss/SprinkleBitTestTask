package ua.varguss.domain.panel.inside;

import lombok.NonNull;
import static ua.varguss.domain.Building.*;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Person;

public class FirstLastFloorsInnerPanel extends AbstractInnerPanel {

    public FirstLastFloorsInnerPanel(@NonNull Elevator elevator) {
        super(elevator);
    }

    @Override
    public void getUsedBy(Person person) {
        if (elevator.getCurrentWeight() + person.getWeight() < elevator.getWeightLimit()) {
            if (person.getDesiredFloor() < elevator.getCurrentFloor())
                elevator.selectFloor(MIN_FLOOR);
            else
                elevator.selectFloor(MAX_FLOOR);
        }
    }
}
