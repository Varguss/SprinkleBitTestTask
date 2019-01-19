package ua.varguss.domain.panel.inside;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ua.varguss.domain.Elevator;

@RequiredArgsConstructor
public abstract class AbstractInnerPanel {
    @NonNull
    private Elevator elevator;

    public abstract void selectFloor(int floor);
}
