package ua.varguss.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Elevator {
    private Map<Integer, Boolean> selectedFloors = new HashMap<>();
    private List<Person> persons;
    private boolean isStopped;
    private int currentFloor;


    {
        selectedFloors.put(1, false);
        selectedFloors.put(2, false);
        selectedFloors.put(3, false);
        selectedFloors.put(4, false);
    }

    public void move() {
        // TODO: implement algorithm
    }

    public void releasePeople() {
        persons.forEach((person) -> {
            if (person.getDesiredFloor() == currentFloor)
                person.getOut();
        });
    }
}
