package ua.varguss.domain.panel.outside;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Floor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractOuterPanel {
    @NonNull
    protected Elevator[] elevators;
    @NonNull
    protected Floor currentFloor;

    public abstract void callElevator(int floor);

    List<Elevator> findUnidirectionalElevators(int floor) {
        List<Elevator> elevators = new ArrayList<>();

        for (Elevator elevator : this.elevators) {
            if (!elevator.isVipInside() || elevator.getSelectedFloorsByVip().get(floor)) {
                if (floor < elevator.getCurrentFloor() && elevator.getDirection() == Elevator.Direction.DOWN)
                    elevators.add(elevator);

                if (floor > elevator.getCurrentFloor() && elevator.getDirection() == Elevator.Direction.UP)
                    elevators.add(elevator);

                if (floor == elevator.getCurrentFloor() && elevator.getDirection() == Elevator.Direction.NONE)
                    elevators.add(elevator);
            }
        }

        return elevators;
    }
}
