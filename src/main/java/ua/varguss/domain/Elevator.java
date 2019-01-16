package ua.varguss.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ua.varguss.domain.Building.*;

@Getter
@EqualsAndHashCode
@ToString
public class Elevator {
    private Map<Integer, Boolean> selectedFloors = new HashMap<>();
    private int currentFloor = 1, speed = 1, currentDistance = 0;
    private Direction direction = Direction.UP;
    private List<Person> personsInside = new ArrayList<>();

    @Setter
    private boolean isStopped;

    {
        for (int i = MIN_FLOOR; i <= MAX_FLOOR; i++)
            selectedFloors.put(i, false);
    }

    private enum Direction {
        UP, DOWN
    }

    /**
     * Начать движение лифта.
     */
    void move() {
        validateMoving();

        if (!isStopped) {
            validateDirection();

            switch (direction) {
                case UP: {
                    moveUp();
                } break;
                case DOWN: {
                    moveDown();
                } break;
            }
        }
    }

    private void validateMoving() {
        if (!isAnySelectedFloor())
            isStopped = true;
    }
    /**
     * Если продолжать движение в раннее выбранном направлении бессмысленно, направление движения изменяется на противоположное.
     */
    private void validateDirection() {
        switch (direction) {
            case UP: {
                if (!isAnySelectedFloorAbove()) {
                    direction = Direction.DOWN;
                    System.out.println("Движение вверх бессмысленно. Смена направления на " + direction.name());
                }
            } break;
            case DOWN: {
                if (!isAnySelectedFloorBelow()) {
                    direction = Direction.UP;
                    System.out.println("Движение вниз бессмысленно. Смена направления на " + direction.name());
                }
            } break;
        }
    }
    /**
     * Движение лифта вверх.
     */
    private void moveUp() {
        System.out.println("Лифт: движение вверх!");
        currentDistance += speed;


        if (currentDistance == FLOOR_HEIGHT) {
            currentDistance = 0;
            currentFloor++;

            System.out.println("Лифт: прибытие на следующий этаж - " + currentFloor);
        }
    }

    /**
     * Движение лифта вниз.
     */
    private void moveDown() {
        System.out.println("Лифт: движение вниз!");
        currentDistance -= speed;

        if (currentDistance == -FLOOR_HEIGHT) {
            currentDistance = 0;
            currentFloor--;

            System.out.println("Лифт: прибытие на следующий этаж - " + currentFloor);
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
    private boolean isAnySelectedFloorBelow() {
        if (currentFloor != MIN_FLOOR)
            for (int i = currentFloor - 1; i >= MIN_FLOOR; i--)
                if (selectedFloors.get(i))
                    return true;

        return false;
    }

    /**
     * Используется для определения, есть ли смысл продолжать движение лифта вообще. Если нет выбранных этажей или этажей, где лифт был вызван, движение бессмыслено.
     * @return true - лифту нужно на какой-либо этаж, false - не нужно.
     */
    private boolean isAnySelectedFloor() {
        for (int i = MIN_FLOOR; i <= MAX_FLOOR; i++)
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
//        personsInside.forEach((person) -> {
//            if (person.getDesiredFloor() == this.currentFloor)
//                person.getOut();
//        });
        for (int i = 0; i < personsInside.size(); i++)
            if (personsInside.get(i).getDesiredFloor() == this.currentFloor)
                personsInside.get(i--).getOut();

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

    /**
     * Есть лифт ещё не сдвинулся с места и кнопка STOP не нажата, лифт не движется.
     * @return true - лифт в движении, false - лифт остановлен.
     */
    boolean isMoving() {
        return currentDistance != 0 && !isStopped;
    }
}