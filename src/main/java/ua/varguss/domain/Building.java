package ua.varguss.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Building {
    private Map<Integer, List<Person>> persons = new HashMap<>();

    @Setter
    @NonNull
    private Elevator elevator;

    static final int MIN_FLOOR = 1, MAX_FLOOR = 4, FLOOR_HEIGHT = 4;

    {
        for (int i = MIN_FLOOR; i <= MAX_FLOOR; i++)
            persons.put(i, new ArrayList<>());
    }

    public void addPerson(Person person) {
        persons.get(person.getCurrentFloor()).add(person);

        System.out.println("Человек по имени '" + person.getName() + "' в доме. Сейчас он на " + person.getCurrentFloor() + " этаже, нужно на " + person.getDesiredFloor() + " этаж");
    }

    public void moveElevator() {
        makePeopleCallElevator();

        elevator.move();

        if (!elevator.isMoving()) {
            updatePeopleInsideElevatorCurrentFloor();
            elevator.releasePeople();
            cleanArrivedPeople();
            fillElevatorWithPeople();
        }
    }

    private void updatePeopleInsideElevatorCurrentFloor() {
        elevator.getPersonsInside().forEach(person -> person.setCurrentFloor(elevator.getCurrentFloor()));
    }

    private void fillElevatorWithPeople() {
        persons.get(elevator.getCurrentFloor()).forEach(person -> person.getIn(elevator));
    }

    private void cleanArrivedPeople() {
        persons.values().forEach(peopleOnFloor -> peopleOnFloor.removeIf(Person::isArrived));
    }

    private void makePeopleCallElevator() {
        persons.values().forEach(people -> people.forEach(person -> {
            if (!person.isInsideElevator() && !person.isCalledElevator())
                person.callElevator(elevator);
        }));
    }
}