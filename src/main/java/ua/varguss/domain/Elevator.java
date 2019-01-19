package ua.varguss.domain;

import lombok.*;
import ua.varguss.domain.panel.inside.AbstractInnerPanel;
import ua.varguss.domain.panel.inside.AllFloorsInnerPanel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ua.varguss.domain.Building.*;

/**
 * Лифт
 */
@Getter
@ToString
@EqualsAndHashCode
public class Elevator {
    private Map<Integer, Boolean> selectedFloors = new HashMap<>();
    private Map<Integer, Boolean> selectedFloorsByVip = new HashMap<>();
    private Map<Integer, Call> calledByFloors = new HashMap<>();
    private int currentFloor = 1, speed = 1, currentDistance = 0, currentWeight = 0, weightLimit = 700;
    private Direction direction = Direction.UP;
    private List<Person> people = new ArrayList<>();
    private AbstractInnerPanel controlPanel;
    private boolean isStopped;

    @Setter
    private boolean isVipInside;

    public Elevator(Class<? extends AbstractInnerPanel> controlPanelClass) {
        try {
            this.controlPanel = controlPanelClass.getConstructor(Elevator.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            this.controlPanel = new AllFloorsInnerPanel(this);
            e.printStackTrace();
        }

        for (int i = MIN_FLOOR; i <= MAX_FLOOR; i++) {
            selectedFloors.put(i, false);
            selectedFloorsByVip.put(i, false);
        }
    }

    /**
     * Направления лифта
     */
    public enum Direction {
        UP, DOWN, NONE
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

    public void setStopped(boolean isStopped) {
        if (isStopped)
            this.direction = Direction.NONE;
        this.isStopped = isStopped;
    }

    public void selectFloor(int floor) {
        selectedFloors.put(floor, true);
    }

    public void selectVipFloor(int floor) {
        selectedFloorsByVip.put(floor, true);
        isVipInside = true;
    }

    /**
     * Проверка, что внутри лифта есть VIP-персона.
     */
    private void validateVipInside() {
        for (Person person : people) {
            if (person.isVip()) {
                isVipInside = true;
                return;
            }
        }

        isVipInside = false;
    }
    /**
     * Если выбранные этажи и вызовы закончились, лифт останавливает работу.
     */
    private void validateMoving() {
        if (!isAnySelectedFloor() && !isStopped) {
            setStopped(true);
            System.out.println("Лифт: выбранных этажов или вызовов нет, остановка");
        }
    }
    /**
     * Если продолжать движение в раннее выбранном направлении бессмысленно, направление движения изменяется на противоположное.
     */
    private void validateDirection() {
        switch (direction) {
            case UP: {
                if (!isAnySelectedFloorAbove()) {
                    direction = Direction.DOWN;
                    System.out.println("Лифт: движение вверх бессмысленно. Смена направления на " + direction.name());
                }
            } break;
            case DOWN: {
                if (!isAnySelectedFloorBelow()) {
                    direction = Direction.UP;
                    System.out.println("Лифт: движение вниз бессмысленно. Смена направления на " + direction.name());
                }
            } break;
        }
    }
    /**
     * Движение лифта вверх.
     */
    private void moveUp() {
        currentDistance += speed;
        System.out.println("Лифт: движение вверх! Пройдено " + currentDistance + "м по шахте, осталось " + (FLOOR_HEIGHT - currentDistance) + "м до следующего этажа");

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
        currentDistance -= speed;
        System.out.println("Лифт: движение вниз! Пройдено " + (-currentDistance) + "м по шахте, осталось " + (FLOOR_HEIGHT + currentDistance) + "м до следующего этажа");

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
     * @param call Информация о вызове.
     */
    public void receiveCall(Call call) {
        calledByFloors.put(call.getDesiredFloor(), calledByFloors.get(call.getDesiredFloor()) == null ? call : call.merge(calledByFloors.get(call.getDesiredFloor())));
        selectFloor(call.getDesiredFloor());
    }

    /**
     * Люди, прибывшие на этаж, который хотели, покидают лифт. Так же, на данный этаж больше никому не нужно.
     */
    void releasePeople() {
        for (int i = 0; i < people.size(); i++)
            if (people.get(i).getDesiredFloor() == this.currentFloor)
                people.get(i--).getOut();

        selectedFloors.put(currentFloor, false);
    }

    void loadPeople(Floor floor) {

    }

    /**
     * Добавление человека в список экипажа.
     * @param person Человек, зашедший в лифт.
     */
    void addPerson(Person person) {
        people.add(person);
    }

    /**
     * Человек покидает лифт.
     * @param person Человек, покидающий лифт.
     */
    void removePerson(Person person) {
        people.remove(person);
    }

    /**
     * Есть лифт ещё не сдвинулся с места и кнопка STOP не нажата, лифт не движется.
     * @return true - лифт в движении, false - лифт остановлен.
     */
    boolean isMoving() {
        return currentDistance != 0 && !isStopped;
    }
}