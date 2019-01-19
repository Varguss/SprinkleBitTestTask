package ua.varguss.domain;

import org.junit.Before;
import org.junit.Test;
import ua.varguss.domain.panel.inside.AllFloorsInnerPanel;

import static org.junit.Assert.*;

public class PersonTest {
    private Person person = new Person("Test", 1, 4);
    private Elevator elevator = new Elevator(AllFloorsInnerPanel.class);

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
}