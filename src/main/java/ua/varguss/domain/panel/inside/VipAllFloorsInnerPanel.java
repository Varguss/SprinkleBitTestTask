package ua.varguss.domain.panel.inside;

import lombok.NonNull;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Person;

public class VipAllFloorsInnerPanel extends AllFloorsInnerPanel {

    public VipAllFloorsInnerPanel(@NonNull Elevator elevator) {
        super(elevator);
    }

    @Override
    public void getUsedBy(Person person) {
        if (elevator.getCurrentWeight() + person.getWeight() < elevator.getWeightLimit()) {
            elevator.selectFloor(person.getDesiredFloor());

            if (person.isVip())
                elevator.selectVipFloor(person.getDesiredFloor());
        }
    }
}
