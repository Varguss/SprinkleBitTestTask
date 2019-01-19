package ua.varguss.domain;

import org.junit.Before;
import org.junit.Test;
import ua.varguss.domain.panel.inside.AbstractInnerPanel;
import ua.varguss.domain.panel.inside.AllFloorsInnerPanel;
import ua.varguss.domain.panel.outside.SingleButtonPanel;

import static org.junit.Assert.*;

public class ElevatorTest {
    private Elevator elevator = new Elevator(AllFloorsInnerPanel.class);

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
    public void releasePeople() {
        Person toRelease = new Person("George", 1, 1);
        toRelease.getIn(elevator);
        elevator.releasePeople();

        assertTrue(!elevator.getPeople().contains(toRelease));
    }

    @Test
    public void addPerson() {
        assertTrue(elevator.getPeople().contains(firstPerson));
    }

    @Test
    public void removePerson() {
        elevator.removePerson(firstPerson);
        assertTrue(!elevator.getPeople().contains(firstPerson));
    }

    @Test
    public void isMoving() {
        assertEquals(0, elevator.getCurrentDistance());
        elevator.move();
        assertEquals(1, elevator.getCurrentDistance());
        assertTrue(!elevator.isStopped());
    }
}