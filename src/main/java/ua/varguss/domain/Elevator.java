package ua.varguss.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@EqualsAndHashCode
@ToString
public class Elevator {
    private Map<Integer, Boolean> selectedFloors = new HashMap<>();
    private int currentFloor = 1, speed = 1, currentDistance = 0;
    private Direction direction = Direction.UP;
    private List<Person> personsInside;

    @Setter
    private boolean isStopped;

    private static final int MIN_FLOOR = 1, MAX_FLOOR = 4, FLOOR_HEIGHT = 4;

    {
        for (int i = MIN_FLOOR; i <= MAX_FLOOR; i++)
            selectedFloors.put(i, false);
    }

    /**
     * Есть лифт ещё не сдвинулся с места и кнопка STOP не нажата, лифт не движется.
     * @return true - лифт в движении, false - лифт остановлен.
     */
    public boolean isMoving() {
        return currentDistance != 0 && !isStopped;
    }

    private enum Direction {
        UP, DOWN
    }

    /**
     * Начать движение лифта.
     */
    public void move() {
        if (!isStopped) {

        }
    }

    /**
     * Движение лифта вверх.
     */
    private void moveUp() {
        currentDistance += speed;

        if (currentDistance == FLOOR_HEIGHT) {
            currentDistance = 0;
            currentFloor++;
        }
    }

    /**
     * Движение лифта вниз.
     */
    private void moveDown() {
        currentDistance -= speed;

        if (currentDistance == -FLOOR_HEIGHT) {
            currentDistance = 0;
            currentFloor--;
        }
    }

    /**
     * Используется внутри алгоритма движения лифта для определения, есть ли ещё выбранные этажи выше текущего этажа.
     * @return true - вверху есть ещё выбранные этажи, false - нет.
     */
    private boolean isAnySelectedFloorAbove() {
        if (currentFloor != MAX_FLOOR)
            for (int i = currentFloor + 1; i <= MAX_FLOOR; i++)
                if (selectedFloors.get(i))
                    return true;

        return false;
    }

    /**
     * Используется внутри алгоритма движения лифта для определения, есть ли ещё выбранные этажи ниже текущего этажа.
     * @return true - внизу есть ещё выбранные этажи, false - нет.
     */
    private boolean isAnySelecteFloorBelow() {
        if (currentFloor != MIN_FLOOR)
            for (int i = currentFloor - 1; i >= MIN_FLOOR; i--)
                if (selectedFloors.get(i))
                    return true;

        return false;
    }

    /**
     * Если лифт вызвали из вне, он должен доехать до этажа, где его вызвали.
     * @param floor Этаж, с которого его вызвали.
     */
    void receiveCall(int floor) {
        selectedFloors.put(floor, true);
    }

    /**
     * Люди, прибывшие на этаж, который хотели, покидают лифт. Так же, на данный этаж больше никому не нужно.
     */
    void releasePeople() {
        personsInside.forEach((person) -> {
            if (person.getDesiredFloor() == this.currentFloor)
                person.getOut();
        });

        selectedFloors.put(currentFloor, false);
    }

    /**
     * Человек входит в лифт и нажимает на кнопку нужного этажа.
     * @param person Человек, зашедший в лифт.
     */
    void addPerson(Person person) {
        personsInside.add(person);

        selectedFloors.put(person.getDesiredFloor(), true);
    }

    /**
     * Человек покидает лифт.
     * @param person Человек, покидающий лифт.
     */
    void removePerson(Person person) {
        personsInside.remove(person);
    }
}