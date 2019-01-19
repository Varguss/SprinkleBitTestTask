package ua.varguss.domain;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.varguss.domain.panel.inside.AllFloorsInnerPanel;

import static org.junit.Assert.*;

public class BuildingTest {
    private Elevator elevator = new Elevator(AllFloorsInnerPanel.class);
    private Building building = new Building();

    private Person firstPerson = new Person("Stephan", 1, 4);
    private Person secondPerson = new Person("Ivan", 3, 2);
    private Person thirdPerson = new Person("Leonardo", 4, 1);

    @Before
    public void setUp() throws Exception {
        building.addPerson(firstPerson);
        building.addPerson(secondPerson);
        building.addPerson(thirdPerson);
    }
}