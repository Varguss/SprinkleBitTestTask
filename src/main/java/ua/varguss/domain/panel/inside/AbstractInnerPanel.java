package ua.varguss.domain.panel.inside;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Person;

@RequiredArgsConstructor
public abstract class AbstractInnerPanel {
    @NonNull
    protected Elevator elevator;

    public abstract void getUsedBy(Person person);
}
