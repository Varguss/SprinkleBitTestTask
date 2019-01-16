package ua.varguss.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BuildingTest {
    private Elevator elevator = new Elevator();
    private Building building = new Building(elevator);

    private Person firstPerson = new Person("Stephan", 1, 4);
    private Person secondPerson = new Person("Ivan", 3, 2);
    private Person thirdPerson = new Person("Leonardo", 4, 1);

    @Before
    public void setUp() throws Exception {
        building.addPerson(firstPerson);
        building.addPerson(secondPerson);
        building.addPerson(thirdPerson);
    }

    @Test
    public void addPerson() {
        assertTrue(building.getPersons().get(1).contains(firstPerson));
        assertTrue(building.getPersons().get(3).contains(secondPerson));
        assertTrue(building.getPersons().get(4).contains(thirdPerson));

        assertFalse(building.getPersons().get(2).contains(firstPerson));
        assertFalse(building.getPersons().get(1).contains(secondPerson));
        assertFalse(building.getPersons().get(3).contains(thirdPerson));
    }

    @Test
    public void moveElevator() {
        Person toRelease = new Person("Test", 1, 1);
        building.addPerson(toRelease);

        building.moveElevator();

        assertTrue(elevator.getPersonsInside().contains(firstPerson));
        assertFalse(elevator.getPersonsInside().contains(toRelease));
        assertFalse(building.getPersons().get(1).contains(toRelease));
    }
}