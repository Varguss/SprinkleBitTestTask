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

    public void cleanArrivedPeople() {
        persons.values().forEach(peopleOnFloor -> peopleOnFloor.removeIf(Person::isArrived));
    }

    public void moveElevator() {
        if (!elevator.isMoving() && !elevator.isStopped()) {
            elevator.releasePeople();
            cleanArrivedPeople();
            fillElevatorWithPeople();
        }


    }

    private void fillElevatorWithPeople() {
        persons.get(elevator.getCurrentFloor()).forEach(person -> person.getIn(elevator));
    }
}