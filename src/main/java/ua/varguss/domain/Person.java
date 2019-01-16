package ua.varguss.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Person {
    private String name;
    private int currentFloor, desiredFloor;
    private Elevator elevator;

    public boolean isInsideElevator() {
        return elevator != null;
    }

    public void getOut() {
        elevator = null;
    }

    public void getIn(Elevator elevator) {
        if (this.elevator == null)
            this.elevator = elevator;
    }
}
