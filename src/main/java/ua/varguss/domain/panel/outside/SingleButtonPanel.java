package ua.varguss.domain.panel.outside;

import lombok.NonNull;
import ua.varguss.domain.Call;
import ua.varguss.domain.Elevator;
import ua.varguss.domain.Floor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SingleButtonPanel extends AbstractOuterPanel {
    public SingleButtonPanel(@NonNull Elevator[] elevators, @NonNull Floor currentFloor) {
        super(elevators, currentFloor);
    }

    @Override
    public void callElevator(int floor) {
        List<Elevator> unidirectionalElevators = findUnidirectionalElevators(floor);

        if (!unidirectionalElevators.isEmpty()) {
            int minimalDifference = Math.abs(unidirectionalElevators.get(0).getCurrentFloor() - floor);

            Elevator optimal = unidirectionalElevators.get(0);
            for (int i = 1; i < unidirectionalElevators.size(); i++) {
                int difference = Math.abs(unidirectionalElevators.get(i).getCurrentFloor() - floor);
                if (difference < minimalDifference) {
                    minimalDifference = difference;
                    optimal = unidirectionalElevators.get(i);
                }
            }

            optimal.receiveCall(new Call(floor, new Elevator.Direction[] { Elevator.Direction.NONE }));
        } else {
            List<Elevator> possibleElevators = Stream.of(elevators)
                    .filter(elevator -> !elevator.isVipInside() || elevator.getSelectedFloorsByVip().get(floor))
                    .collect(Collectors.toList());

            int minimalDifference = Math.abs(possibleElevators.get(0).getCurrentFloor() - floor);

            Elevator optimal = possibleElevators.get(0);
            for (int i = 1; i < possibleElevators.size(); i++) {
                int difference = Math.abs(possibleElevators.get(i).getCurrentFloor() - floor);
                if (difference < minimalDifference) {
                    minimalDifference = difference;
                    optimal = possibleElevators.get(i);
                }
            }

            optimal.receiveCall(new Call(floor, new Elevator.Direction[] { Elevator.Direction.NONE }));
        }
    }
}
