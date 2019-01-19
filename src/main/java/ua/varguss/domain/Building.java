package ua.varguss.domain;

import lombok.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Здание (класс-фасад) для объединения людей, которые внутри здания, и лифта, который пренадлежит этому зданию.
 */
@Getter
@RequiredArgsConstructor
public class Building {
    /**
     * Ключ - номер этажа, значение - этаж.
     */
    private Map<Integer, Floor> floors = new HashMap<>();

    /**
     * Лифт внутри здания.
     */
    @Setter
    @NonNull
    private Elevator elevator;

    static final int MIN_FLOOR = 1, MAX_FLOOR = 4, FLOOR_HEIGHT = 4;

    {
        for (int i = MIN_FLOOR; i <= MAX_FLOOR; i++)
            floors.put(i, new Floor(elevator, i, FLOOR_HEIGHT));
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

        if (!elevator.isMoving()) {
            fillElevatorWithPeople();
            elevator.releasePeople();
            cleanArrivedPeople();
        }

        elevator.move();

        if (!elevator.isMoving())
            updatePeopleInsideElevatorCurrentFloor();
    }

    /**
     * Если лифт прибыл на новый этаж, значит, люди в этом лифте тоже прибыли на новый этаж.
     */
    private void updatePeopleInsideElevatorCurrentFloor() {
        elevator.getPersonsInside().forEach(person -> person.setCurrentFloor(elevator.getCurrentFloor()));
    }

    /**
     * Если на текущем этаже лифта есть люди, они входят в лифт.
     */
    private void fillElevatorWithPeople() {
        floors.get(elevator.getCurrentFloor()).getPeople().forEach(person -> person.getIn(elevator));
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
                person.callElevator(elevator);
        }));
    }
}