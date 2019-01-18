package ua.varguss.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Здание (класс-фасад) для объединения людей, которые внутри здания, и лифта, который пренадлежит этому зданию.
 */
@Getter
@RequiredArgsConstructor
public class Building {
    /**
     * Ключ - номер этажа, на котором люди находились изначально, значение - список людей, которые находились на этом этаже.
     */
    private Map<Integer, List<Person>> persons = new HashMap<>();

    /**
     * Лифт внутри здания.
     */
    @Setter
    @NonNull
    private Elevator elevator;

    static final int MIN_FLOOR = 1, MAX_FLOOR = 4, FLOOR_HEIGHT = 4;

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @RequiredArgsConstructor
    class Floor {
        private List<Person> people = new ArrayList<>();

        @NonNull
        private int number;
        @NonNull
        private int height;
        @NonNull
        private AbstractOuterPanel outerPanel;

        @Getter
        @Setter
        @RequiredArgsConstructor
        abstract class AbstractOuterPanel {
            @NonNull
            private Elevator elevator;

            public abstract void callElevator(int desiredFloor);
        }

        class UpDownPanel extends AbstractOuterPanel {
            public UpDownPanel(@NonNull Elevator elevator) {
                super(elevator);
            }

            @Override
            public void callElevator(int desiredFloor) {
                // TODO: don't forget to implement
            }
        }

        class SingleButtonPanel extends AbstractOuterPanel {
            public SingleButtonPanel(@NonNull Elevator elevator) {
                super(elevator);
            }

            @Override
            public void callElevator(int desiredFloor) {
                // TODO: don't forget to implement
            }
        }
    }

    {
        for (int i = MIN_FLOOR; i <= MAX_FLOOR; i++)
            persons.put(i, new ArrayList<>());
    }

    /**
     * Добавить человека внутрь здания.
     * @param person Человек.
     */
    public void addPerson(Person person) {
        persons.get(person.getCurrentFloor()).add(person);

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
        persons.get(elevator.getCurrentFloor()).forEach(person -> person.getIn(elevator));
    }

    /**
     * Люди, которые успешно добрались до нужного этажа, очищаются.
     */
    private void cleanArrivedPeople() {
        persons.values().forEach(peopleOnFloor -> peopleOnFloor.removeIf(Person::isArrived));
    }

    /**
     * Люди должны вызвать лифт, чтобы он гарантировано забрал их.
     */
    private void makePeopleCallElevator() {
        persons.values().forEach(people -> people.forEach(person -> {
            if (!person.isInsideElevator() && !person.isCalledElevator())
                person.callElevator(elevator);
        }));
    }
}