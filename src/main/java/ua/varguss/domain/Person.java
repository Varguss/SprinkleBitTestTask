package ua.varguss.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Person {
    private String name;
    private int desiredFloor;
    private Elevator elevator;
    private boolean calledElevator;

    @Setter
    private int currentFloor;

    public Person(String name, int currentFloor, int desiredFloor) {
        this.name = name;
        this.currentFloor = currentFloor;
        this.desiredFloor = desiredFloor;
    }

    boolean isInsideElevator() {
        return elevator != null;
    }

    boolean isArrived() {
        return currentFloor == desiredFloor;
    }

    void getOut() {
        if (elevator != null) {
            elevator.removePerson(this);
            elevator = null;
        }

        System.out.println("Человек по имени '" + name + "' вышел из лифта на " + currentFloor + " этаже");
    }

    void getIn(Elevator elevator) {
        if (this.elevator == null) {
            elevator.addPerson(this);
            this.elevator = elevator;
            calledElevator = false;

            System.out.println("Человек по имени '" + name + "' вошел в лифт на " + currentFloor + " этаже");
        }
    }

    public void pushStopButton() {
        if (isInsideElevator())
            elevator.setStopped(!elevator.isStopped());
    }

    void callElevator(Elevator elevator) {
        if (!isInsideElevator()) {
            elevator.receiveCall(currentFloor);
            calledElevator = true;
            System.out.println("Человек по имени '" + name + "' позвал лифт на " + currentFloor + " этаж");
        }
    }
}