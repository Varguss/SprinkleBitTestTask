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

    public Person(String name, int currentFloor, int desiredFloor) {
        this.name = name;
        this.currentFloor = currentFloor;
        this.desiredFloor = desiredFloor;
    }

    public boolean isInsideElevator() {
        return elevator != null;
    }

    public boolean isArrived() {
        return currentFloor == desiredFloor;
    }

    public void getOut() {
        if (elevator != null) {
            elevator.removePerson(this);
            elevator = null;
        }
    }

    public void getIn(Elevator elevator) {
        if (this.elevator == null) {
            elevator.addPerson(this);
            this.elevator = elevator;
        }
    }

    public void pushStopButton() {
        if (isInsideElevator())
            elevator.setStopped(!elevator.isStopped());
    }
}