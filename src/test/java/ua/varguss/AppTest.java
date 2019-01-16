package ua.varguss;

import org.junit.Before;
import org.junit.Test;
import ua.varguss.domain.Building;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Person;

import static org.junit.Assert.*;

public class AppTest {
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
    public void main() {
        while (!elevator.isStopped()) {
            building.moveElevator();
        }

        assertTrue(firstPerson.isArrived());
        assertTrue(secondPerson.isArrived());
        assertTrue(thirdPerson.isArrived());
    }
}