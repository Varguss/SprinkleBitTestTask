package ua.varguss.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Человек
 */
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

    /**
     * Человек внутри лифта?
     * @return true - внутри лифта, false - не в лифте.
     */
    boolean isInsideElevator() {
        return elevator != null;
    }

    /**
     * Человек прибыл на нужный этаж?
     * @return true - прибыл, false - не прибыл.
     */
    boolean isArrived() {
        return currentFloor == desiredFloor;
    }

    /**
     * Выйти из лифта, если раннее был в лифте.
     */
    void getOut() {
        if (isInsideElevator()) {
            elevator.removePerson(this);
            elevator = null;
        }

        System.out.println("Человек по имени '" + name + "' вышел из лифта на " + currentFloor + " этаже");
    }

    /**
     * Войти в лифт, если ещё не в лифте.
     * @param elevator Лифт.
     */
    void getIn(Elevator elevator) {
        if (!isInsideElevator()) {
            elevator.addPerson(this);
            this.elevator = elevator;
            calledElevator = false;

            System.out.println("Человек по имени '" + name + "' вошел в лифт на " + currentFloor + " этаже");
        }
    }

    /**
     * Нажать на кнопку STOP лифта, если в лифте.
     */
    public void pushStopButton() {
        if (isInsideElevator()) {
            elevator.setStopped(!elevator.isStopped());
            System.out.println("Человек по имени '" + name + "' нажал на кнопку STOP. Лифт " + (elevator.isStopped() ? "остановился" : "продолжает работу"));
        }
    }

    /**
     * Позвать лифт на текущий этаж.
     * @param elevator Лифт.
     */
    void callElevator(Elevator elevator) {
        if (!isInsideElevator()) {
            elevator.receiveCall(currentFloor);
            calledElevator = true;
            System.out.println("Человек по имени '" + name + "' позвал лифт на " + currentFloor + " этаж");
        }
    }
}