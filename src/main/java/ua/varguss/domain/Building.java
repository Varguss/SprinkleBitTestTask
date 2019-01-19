package ua.varguss.domain;

import lombok.*;
import ua.varguss.domain.panel.inside.FirstLastFloorsInnerPanel;
import ua.varguss.domain.panel.inside.VipAllFloorsInnerPanel;

import java.util.HashMap;
import java.util.Map;

/**
 * Здание (класс-фасад) для объединения людей, которые внутри здания, и лифта, который пренадлежит этому зданию.
 */
@Getter
public class Building {
    /**
     * Ключ - номер этажа, значение - этаж.
     */
    private Map<Integer, Floor> floors = new HashMap<>();

    /**
     * Лифты внутри здания.
     */
    private Elevator[] elevators = new Elevator[2];

    public static final int MIN_FLOOR = 1, MAX_FLOOR = 10, FLOOR_HEIGHT = 4;

    {
        elevators[0] = new Elevator(VipAllFloorsInnerPanel.class);
        elevators[1] = new Elevator(FirstLastFloorsInnerPanel.class);

        for (int i = MIN_FLOOR; i <= MAX_FLOOR; i++)
            floors.put(i, new Floor(elevators, i, FLOOR_HEIGHT));
    }

    /**
     * Добавить человека внутрь здания.
     * @param person Человек.
     */
    public void addPerson(Person person) {
        floors.get(person.getCurrentFloor()).addPerson(person);
        System.out.println("Человек по имени '" + person.getName() + "' в доме. Сейчас он на " + person.getCurrentFloor() + " этаже, нужно на " + person.getDesiredFloor() + " этаж");
    }

    public boolean hasActiveElevators() {
        for (Elevator elevator : elevators)
            if (!elevator.isStopped())
                return true;
        return false;
    }
    /**
     * Осуществляет контроль движения лифта и людей внутри здания.
     */
    public void moveElevator() {
        makePeopleCallElevator();
        cleanArrivedPeople();

        for (Elevator elevator : elevators) {
            if (!elevator.isMoving()) {
                fillElevatorsWithPeople();
            }

            elevator.move();
        }
    }

    /**
     * Если на текущем этаже лифта есть люди, они входят в лифт.
     */
    private void fillElevatorsWithPeople() {
        for (Elevator elevator : elevators) {
            if ((!elevator.isVipInside() || elevator.getSelectedFloorsByVip().get(elevator.getCurrentFloor())) && !elevator.isStopped())
                floors.get(elevator.getCurrentFloor()).getPeople().forEach(person -> {
                    if (!person.isInsideElevator() && !person.isArrived())
                        person.getIn(elevator);
                });
        }
    }

    /**
     * Люди, которые успешно добрались до нужного этажа, очищаются.
     */
    private void cleanArrivedPeople() {
        floors.values().forEach(floor -> floor.getPeople().removeIf(Person::isArrived));
    }

    /**
     * Люди должны вызвать лифт, чтобы он гарантировано забрал их.
     */
    private void makePeopleCallElevator() {
        floors.values().forEach(floor -> floor.getPeople().forEach(person -> {
            person.callElevator(floor.getOuterPanel());
        }));
    }
}