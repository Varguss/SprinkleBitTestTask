package ua.varguss.domain.panel.outside;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ua.varguss.domain.Elevator;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractOuterPanel {
    @NonNull
    protected Elevator elevator;

    public abstract void callElevator(int desiredFloor);
}
