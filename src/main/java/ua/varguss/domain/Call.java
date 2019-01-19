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
    private Elevator.Direction direction;
}
