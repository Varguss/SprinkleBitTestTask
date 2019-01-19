package ua.varguss.domain;

import lombok.*;

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

    public Call merge(Call another) {
        Elevator.Direction[] directions = new Elevator.Direction[Math.max(this.directions.length, another.directions.length)];

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
                directions[fromIndex++] = thisDirection;
            }
        }

        return fromIndex;
    }
}
