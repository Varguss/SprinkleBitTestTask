package ua.varguss.domain;

import lombok.*;

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

    public void addPerson(Person person) {
        persons.get(person.getCurrentFloor()).add(person);
    }

    public void moveElevator() {
        makePeopleCallElevator();

        if (!elevator.isMoving()) {
            updatePeopleInsideElevatorCurrentFloor();
            elevator.releasePeople();
            cleanArrivedPeople();
            fillElevatorWithPeople();
        }

        elevator.move();
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
            if (!person.isInsideElevator())
                person.callElevator(elevator);
        }));
    }
}