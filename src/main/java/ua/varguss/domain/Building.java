package ua.varguss.domain;

import lombok.*;
import ua.varguss.domain.panel.inside.AllFloorsInnerPanel;
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
     * Лифт внутри здания.
     */

    private Elevator[] elevators = new Elevator[2];

    static final int MIN_FLOOR = 1, MAX_FLOOR = 4, FLOOR_HEIGHT = 4;

    {
        elevators[0] = new Elevator(VipAllFloorsInnerPanel.class);
        elevators[1] = new Elevator(FirstLastFloorsInnerPanel.class);

        for (int i = MIN_FLOOR; i <= MAX_FLOOR; i++)
            floors.put(i, new Floor(elevators[0], i, FLOOR_HEIGHT));
    }

    /**
     * Добавить человека внутрь здания.
     * @param person Человек.
     */
    public void addPerson(Person person) {
        floors.get(person.getCurrentFloor()).addPerson(person);
        System.out.println("Человек по имени '" + person.getName() + "' в доме. Сейчас он на " + person.getCurrentFloor() + " этаже, нужно на " + person.getDesiredFloor() + " этаж");
    }

    /**
     * Осуществляет контроль движения лифта и людей внутри здания.
     */
    public void moveElevator() {
        makePeopleCallElevator();

        if (!elevators[0].isMoving()) {
            fillElevatorWithPeople();
            elevators[0].releasePeople();
            cleanArrivedPeople();
        }

        elevators[0].move();

        if (!elevators[0].isMoving())
            updatePeopleInsideElevatorCurrentFloor();
    }

    /**
     * Если лифт прибыл на новый этаж, значит, люди в этом лифте тоже прибыли на новый этаж.
     */
    private void updatePeopleInsideElevatorCurrentFloor() {
        elevators[0].getPeople().forEach(person -> person.setCurrentFloor(elevators[0].getCurrentFloor()));
    }

    /**
     * Если на текущем этаже лифта есть люди, они входят в лифт.
     */
    private void fillElevatorWithPeople() {
        floors.get(elevators[0].getCurrentFloor()).getPeople().forEach(person -> person.getIn(elevators[0]));
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
            if (!person.isInsideElevator() && !person.isCalledElevator())
                person.callElevator(elevators[0]);
        }));
    }
}