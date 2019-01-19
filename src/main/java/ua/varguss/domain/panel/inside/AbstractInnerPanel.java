package ua.varguss.domain.panel.inside;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Person;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class AbstractInnerPanel {
    @NonNull
    protected Elevator elevator;

    public abstract void getUsedBy(Person person);
}
