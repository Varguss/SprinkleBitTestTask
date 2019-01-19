package ua.varguss.domain;

import lombok.*;
import ua.varguss.domain.panel.outside.AbstractOuterPanel;
import ua.varguss.domain.panel.outside.SingleButtonPanel;
import ua.varguss.domain.panel.outside.UpDownPanel;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Floor {
    private List<Person> people = new ArrayList<>();
    private AbstractOuterPanel outerPanel;

    @NonNull
    private int number;
    @NonNull
    private int height;

    public Floor(Elevator[] elevators, int number, int height) {
        this.outerPanel = Math.random() > 0.5d ? new UpDownPanel(elevators) : new SingleButtonPanel(elevators);
        this.number = number;
        this.height = height;
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public void removePerson(Person person) {
        this.people.remove(person);
    }
}
