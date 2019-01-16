package ua.varguss.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ElevatorTest {
    private Elevator elevator = new Elevator();

    private Person firstPerson = new Person("Stephan", 1, 4);
    private Person secondPerson = new Person("Ivan", 3, 2);

    @Before
    public void setUp() throws Exception {
        firstPerson.getIn(elevator);
    }

    @Test
    public void move() {
        elevator.move();

        assertTrue(elevator.isMoving());
    }

    @Test
    public void receiveCall() {
        secondPerson.callElevator(elevator);

        assertTrue(elevator.getSelectedFloors().get(3));
    }

    @Test
    public void releasePeople() {
        Person toRelease = new Person("George", 1, 1);
        toRelease.getIn(elevator);
        elevator.releasePeople();

        assertTrue(!elevator.getPersonsInside().contains(toRelease));
    }

    @Test
    public void addPerson() {
        assertTrue(elevator.getPersonsInside().contains(firstPerson));
    }

    @Test
    public void removePerson() {
        elevator.removePerson(firstPerson);
        assertTrue(!elevator.getPersonsInside().contains(firstPerson));
    }

    @Test
    public void isMoving() {
        assertEquals(0, elevator.getCurrentDistance());
        elevator.move();
        assertEquals(1, elevator.getCurrentDistance());
        assertTrue(!elevator.isStopped());
    }
}