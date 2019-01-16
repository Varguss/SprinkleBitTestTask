package ua.varguss.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {
    private Person person = new Person("Test", 1, 4);
    private Elevator elevator = new Elevator();

    @Test
    public void isInsideElevator() {
        assertNull(person.getElevator());
        person.getIn(elevator);
        assertNotNull(person.getElevator());
    }

    @Test
    public void isArrived() {
        person.setCurrentFloor(4);
        assertEquals(person.getCurrentFloor(), person.getDesiredFloor());
    }

    @Test
    public void getOut() {
        person.getIn(elevator);
        assertNotNull(person.getElevator());
        person.getOut();
        assertNull(person.getElevator());
    }

    @Test
    public void getIn() {
        assertNull(person.getElevator());
        person.getIn(elevator);
        assertNotNull(person.getElevator());
    }

    @Test
    public void pushStopButton() {
        person.getIn(elevator);
        assertTrue(!elevator.isStopped());
        person.pushStopButton();
        assertTrue(elevator.isStopped());
        person.pushStopButton();
        assertTrue(!elevator.isStopped());
    }

    @Test
    public void callElevator() {
        person.callElevator(elevator);
        assertTrue(elevator.getSelectedFloors().get(person.getCurrentFloor()));
    }

    @Test
    public void isCalledElevator() {
        assertFalse(person.isCalledElevator());
        person.callElevator(elevator);
        assertTrue(person.isCalledElevator());
    }
}