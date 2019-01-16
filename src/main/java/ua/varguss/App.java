package ua.varguss;

import ua.varguss.domain.Building;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Building building = new Building(new Elevator());

        Person firstPerson = new Person("Stephan", 1, 4);
        Person secondPerson = new Person("Ivan", 3, 2);
        Person thirdPerson = new Person("Leonardo", 4, 1);

        building.addPerson(firstPerson);
        building.addPerson(secondPerson);
        building.addPerson(thirdPerson);
    }
}
