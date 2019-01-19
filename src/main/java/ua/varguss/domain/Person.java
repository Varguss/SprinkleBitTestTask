package ua.varguss.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ua.varguss.domain.panel.outside.AbstractOuterPanel;

/**
 * Человек
 */
@Getter
@ToString
@EqualsAndHashCode
public class Person {
    private String name;
    private int desiredFloor;
    private int weight;
    private Elevator elevator;
    private boolean calledElevator;
    private boolean vip;

    @Setter
    private int currentFloor;

    public Person(String name, int currentFloor, int desiredFloor) {
        this.name = name;
        this.currentFloor = currentFloor;
        this.desiredFloor = desiredFloor;
        this.weight = 70;
    }

    public Person(String name, int currentFloor, int desiredFloor, boolean vip) {
        this.name = name;
        this.currentFloor = currentFloor;
        this.desiredFloor = desiredFloor;
        this.vip = vip;
        this.weight = 70;
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
    public boolean isArrived() {
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

    void requestDesiredFloor() {
        if (isInsideElevator()) {
            elevator.getControlPanel().getUsedBy(this);
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
     * @param outerPanel Внешняя панель вызова лифта.
     */
    void callElevator(AbstractOuterPanel outerPanel) {
        if (!isInsideElevator() && !calledElevator) {
            outerPanel.callElevator(desiredFloor);
            calledElevator = true;
            System.out.println("Человек по имени '" + name + "' позвал лифт на " + currentFloor + " этаж");
        }
    }
}