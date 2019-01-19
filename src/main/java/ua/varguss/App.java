package ua.varguss;

import ua.varguss.domain.Building;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Person;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        Building building = new Building();

        Person p1 = new Person("1", 1, 4);
        Person p2 = new Person("2", 3, 2);
        Person p3 = new Person("3", 4, 1);
        Person p4 = new Person("4", 7, 2);
        Person p5 = new Person("5", 4, 1);
        Person p6 = new Person("6", 5, 2, true);
        Person p7 = new Person("7", 10, 9);
        Person p8 = new Person("8", 9, 10);
        Person p9 = new Person("9", 1, 10);
        Person p10 = new Person("10", 10, 4, true);
        Person p11 = new Person("11", 5, 4);
        Person p12 = new Person("12", 4, 5);
        Person p13 = new Person("13", 2, 7);
        Person p14 = new Person("14", 7, 8, true);
        Person p15 = new Person("15", 5, 2);
        Person p16 = new Person("16", 1, 3);
        Person p17 = new Person("17", 4, 2);
        Person p18 = new Person("18", 7, 8);
        Person p19 = new Person("19", 9, 2, true);
        Person p20 = new Person("20", 4, 7, true);

        building.addPerson(p1);
        building.addPerson(p2);
        building.addPerson(p3);
        building.addPerson(p4);
        building.addPerson(p5);
        building.addPerson(p6);
        building.addPerson(p7);
        building.addPerson(p8);
        building.addPerson(p9);
        building.addPerson(p10);
        building.addPerson(p11);
        building.addPerson(p12);
        building.addPerson(p13);
        building.addPerson(p14);
        building.addPerson(p15);
        building.addPerson(p16);
        building.addPerson(p17);
        building.addPerson(p18);
        building.addPerson(p19);
        building.addPerson(p20);

        while (building.hasActiveElevators()) {
            building.moveElevator();
            Thread.sleep(1000);
        }

        building.getFloors().forEach((key, floor) -> floor.getPeople().forEach(System.out::println));
    }
}
