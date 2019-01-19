package ua.varguss.domain.panel.outside;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ua.varguss.domain.Call;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Floor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractOuterPanel {
    @NonNull
    protected Elevator[] elevators;
    @NonNull
    protected Floor currentFloor;

    public abstract boolean callElevator(int floor);

    boolean callElevator(int floor, Elevator.Direction[] directions) {
        List<Elevator> unidirectionalElevators = findUnidirectionalElevators(floor);

        if (!unidirectionalElevators.isEmpty()) {
            int minimalDifference = Math.abs(unidirectionalElevators.get(0).getCurrentFloor() - floor) + unidirectionalElevators.get(0).getBusyIndicator();

            Elevator optimal = unidirectionalElevators.get(0);
            for (int i = 1; i < unidirectionalElevators.size(); i++) {
                int difference = Math.abs(unidirectionalElevators.get(i).getCurrentFloor() - floor) + unidirectionalElevators.get(i).getBusyIndicator();
                if (difference < minimalDifference) {
                    minimalDifference = difference;
                    optimal = unidirectionalElevators.get(i);
                }
            }

            optimal.receiveCall(new Call(floor, directions));
            return true;
        } else {
            List<Elevator> possibleElevators = Stream.of(elevators)
                    .filter(elevator -> !elevator.isVipInside() || elevator.getSelectedFloorsByVip().get(floor))
                    .collect(Collectors.toList());

            if (possibleElevators.isEmpty())
                return false;

            int minimalDifference = Math.abs(possibleElevators.get(0).getCurrentFloor() - floor) + possibleElevators.get(0).getBusyIndicator();

            Elevator optimal = possibleElevators.get(0);
            for (int i = 1; i < possibleElevators.size(); i++) {
                int difference = Math.abs(possibleElevators.get(i).getCurrentFloor() - floor) + possibleElevators.get(i).getBusyIndicator();
                if (difference < minimalDifference) {
                    minimalDifference = difference;
                    optimal = possibleElevators.get(i);
                }
            }

            optimal.receiveCall(new Call(floor, directions));
            return true;
        }
    }

    private List<Elevator> findUnidirectionalElevators(int floor) {
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
