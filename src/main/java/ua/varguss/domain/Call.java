package ua.varguss.domain;

import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Call {
    @NonNull
    private int desiredFloor;
    @NonNull
    private Elevator.Direction[] directions;

    Call merge(Call another) {
        Set<Elevator.Direction> foundedDirections = new HashSet<>();

        Collections.addAll(foundedDirections, this.directions);
        Collections.addAll(foundedDirections, another.directions);

        Elevator.Direction[] directions = new Elevator.Direction[foundedDirections.size()];

        int lastIndex = mergeDirections(directions, this.directions, 0);
        mergeDirections(directions, another.directions, lastIndex);

        return new Call(this.desiredFloor, directions);
    }

    private int mergeDirections(Elevator.Direction[] targetDirections, Elevator.Direction[] toMergeDirections, int fromIndex) {
        for (Elevator.Direction thisDirection : toMergeDirections) {
            boolean contains = false;

            for (Elevator.Direction thatDirection : targetDirections) {
                if (thisDirection == thatDirection) {
                    contains = true;
                    break;
                }
            }

            if (!contains) {
                targetDirections[fromIndex++] = thisDirection;
            }
        }

        return fromIndex;
    }
}
